export enum ConversionType {
  DISTANCE = 'DISTANCE',
  WEIGHT = 'WEIGHT',
  TEMPERATURE = 'TEMPERATURE',
  CURRENCY = 'CURRENCY',
  VOLUME = 'VOLUME',
  TIME = 'TIME',
  AREA = 'AREA',
}

export interface ConversionRequest {
  value: number
  fromUnit: string
  toUnit: string
  type: ConversionType
  options?: Record<string, any>
}

export interface ConversionResult {
  originalValue: number
  originalUnit: string
  convertedValue: number
  convertedUnit: string
  type: ConversionType
  formula?: string
  timestamp: string
  success: boolean
  errorMessage?: string
}
