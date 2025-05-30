import type { ConversionRequest, ConversionResult, ApiResponse } from '@/types'

class ApiService {
  private readonly baseUrl: string

  constructor(baseUrl: string = 'http://localhost:8081/api') {
    this.baseUrl = baseUrl
  }

  async convert(request: ConversionRequest): Promise<ApiResponse<ConversionResult>> {
    try {
      const response = await fetch(`${this.baseUrl}/convert`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(request),
      })

      const data: ConversionResult = await response.json()

      if (!response.ok) {
        return {
          success: false,
          error: data.errorMessage || `HTTP ${response.status}`,
        }
      }

      return {
        success: true,
        data,
      }
    } catch (error) {
      return {
        success: false,
        error: error instanceof Error ? error.message : 'Network error',
      }
    }
  }

  async healthCheck(): Promise<boolean> {
    try {
      const response = await fetch(`${this.baseUrl}/convert/health`)
      return response.ok
    } catch {
      return false
    }
  }
}

export const apiService = new ApiService()
export default apiService
