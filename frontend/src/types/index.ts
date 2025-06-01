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
  timestamp?: string
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

export interface ExchangeRateResponse {
  base: string
  date: string
  rates: Record<string, number>
}

export interface ConversionModule {
  id: string
  type: ConversionType
  title: string
  icon: string
  color: string
  isActive?: boolean
  isFavorite?: boolean
}

export interface ModuleState {
  value: number | null
  fromUnit: string
  toUnit: string
  result: ConversionResult | null
  isLoading: boolean
  error: string | null
}

export interface AppConfig {
  apiBaseUrl: string
  defaultModules: ConversionType[]
  maxFavoriteModules: number
}

export interface UnitCategory {
  category: ConversionType
  units: Unit[]
}

export interface Unit {
  symbol: string
  name: string
  category: string
  isMetric?: boolean
  isImperial?: boolean
}
