import type { ConversionRequest, ConversionResult, ConversionType } from '@/types'

const API_BASE_URL = 'http://localhost:8081/api'

export class ApiError extends Error {
  constructor(
    message: string,
    public status?: number,
  ) {
    super(message)
    this.name = 'ApiError'
  }
}

export class ApiService {
  private async makeRequest<T>(endpoint: string, options: RequestInit = {}): Promise<T> {
    const url = `${API_BASE_URL}${endpoint}`

    const defaultOptions: RequestInit = {
      headers: {
        'Content-Type': 'application/json',
      },
    }

    const mergedOptions = {
      ...defaultOptions,
      ...options,
      headers: {
        ...defaultOptions.headers,
        ...options.headers,
      },
    }

    try {
      const response = await fetch(url, mergedOptions)

      if (!response.ok) {
        throw new ApiError(
          `Request failed: ${response.status} ${response.statusText}`,
          response.status,
        )
      }

      return await response.json()
    } catch (error) {
      if (error instanceof ApiError) {
        throw error
      }

      throw new ApiError(
        `Network error: ${error instanceof Error ? error.message : 'Unknown error'}`,
      )
    }
  }

  async convert(request: ConversionRequest): Promise<ConversionResult> {
    return this.makeRequest<ConversionResult>('/convert', {
      method: 'POST',
      body: JSON.stringify(request),
    })
  }

  async healthCheck(): Promise<string> {
    return this.makeRequest<string>('/convert/health')
  }

  createConversionRequest(
    value: number,
    fromUnit: string,
    toUnit: string,
    type: ConversionType,
  ): ConversionRequest {
    return {
      value,
      fromUnit,
      toUnit,
      type,
      timestamp: new Date().toISOString(),
    }
  }
}

export const apiService = new ApiService()

export const convertUnits = (request: ConversionRequest): Promise<ConversionResult> => {
  return apiService.convert(request)
}

export const checkApiHealth = (): Promise<string> => {
  return apiService.healthCheck()
}
