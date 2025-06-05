<template>
  <div class="dashboard">
    <div class="dashboard-header">
      <h1>Unit Converter Pro</h1>

      <div class="dashboard-controls">
        <el-button-group>
          <el-button size="small"> ⭐ Favoris </el-button>
          <el-button size="small"> 🔄 Effacer </el-button>
        </el-button-group>
      </div>
    </div>

    <div class="modules-grid">
      <div v-for="module in modules" :key="module.id" class="module-wrapper">
        <el-card class="module-card">
          <div class="module-header">
            <span class="module-icon">{{ module.icon }}</span>
            <h3>{{ module.title }}</h3>
            <el-button size="small" circle>⭐</el-button>
          </div>

          <div class="module-content">
            <el-input
              v-model.number="module.inputValue"
              placeholder="Entrez une valeur"
              type="number"
              size="large"
            />

            <div class="units-row">
              <el-select v-model="module.fromUnit" placeholder="De" size="large">
                <el-option
                  v-for="unit in getUnitsFor(module.id)"
                  :key="unit.value"
                  :label="unit.label"
                  :value="unit.value"
                />
              </el-select>

              <el-button circle size="small" @click="swapUnits(module)"> ⇄ </el-button>

              <el-select v-model="module.toUnit" placeholder="Vers" size="large">
                <el-option
                  v-for="unit in getUnitsFor(module.id)"
                  :key="unit.value"
                  :label="unit.label"
                  :value="unit.value"
                />
              </el-select>
            </div>

            <el-button
              type="primary"
              size="large"
              block
              :loading="module.isLoading"
              :disabled="!canConvert(module)"
              @click="convertModule(module)"
            >
              {{ module.isLoading ? 'Conversion...' : 'Convertir' }}
            </el-button>

            <div v-if="module.result" class="result-box">
              <div class="result-value">
                {{ formatResult(module.result.convertedValue) }}
                <span class="result-unit">{{ module.result.convertedUnit }}</span>
              </div>
              <div v-if="module.result.formula" class="result-formula">
                {{ module.result.formula }}
              </div>
            </div>

            <div v-if="module.error" class="error-box">
              <el-alert :title="module.error" type="error" show-icon />
            </div>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { apiService } from '@/services/api'
import { ConversionType } from '@/types'

const modules = ref([
  {
    id: 'distance',
    title: 'Distance',
    icon: '📏',
    color: '#3b82f6',
    type: 'DISTANCE' as ConversionType,
    inputValue: null,
    fromUnit: '',
    toUnit: '',
    result: null,
    error: null,
    isLoading: false,
  },
  {
    id: 'weight',
    title: 'Poids',
    icon: '⚖️',
    color: '#10b981',
    type: 'WEIGHT' as ConversionType,
    inputValue: null,
    fromUnit: '',
    toUnit: '',
    result: null,
    error: null,
    isLoading: false,
  },
  {
    id: 'temperature',
    title: 'Température',
    icon: '🌡️',
    color: '#f59e0b',
    type: 'TEMPERATURE' as ConversionType,
    inputValue: null,
    fromUnit: '',
    toUnit: '',
    result: null,
    error: null,
    isLoading: false,
  },
  {
    id: 'currency',
    title: 'Devise',
    icon: '💰',
    color: '#8b5cf6',
    type: 'CURRENCY' as ConversionType,
    inputValue: null,
    fromUnit: '',
    toUnit: '',
    result: null,
    error: null,
    isLoading: false,
  },
])

// Fonction pour échanger les unités
const swapUnits = (module: any) => {
  const temp = module.fromUnit
  module.fromUnit = module.toUnit
  module.toUnit = temp
}

// Vérifier si on peut convertir
const canConvert = (module: any) => {
  return (
    module.inputValue &&
    module.inputValue > 0 &&
    module.fromUnit &&
    module.toUnit &&
    module.fromUnit !== module.toUnit
  )
}

// Fonction de conversion
const convertModule = async (module: any) => {
  module.isLoading = true
  module.error = null
  module.result = null

  try {
    const request = apiService.createConversionRequest(
      module.inputValue,
      module.fromUnit,
      module.toUnit,
      module.type,
    )

    const result = await apiService.convert(request)
    module.result = result
  } catch (error) {
    module.error = error instanceof Error ? error.message : 'Erreur de conversion'
  } finally {
    module.isLoading = false
  }
}

// Formater le résultat
const formatResult = (value: number) => {
  if (value === 0) return '0'

  if (Math.abs(value) >= 1000000) {
    return value.toExponential(3)
  }

  if (Math.abs(value) < 0.001) {
    return value.toExponential(3)
  }

  return new Intl.NumberFormat('fr-FR', {
    maximumFractionDigits: 6,
  }).format(value)
}

// Obtenir les unités selon le type
const getUnitsFor = (moduleId: string) => {
  const unitMap = {
    distance: [
      { value: 'mm', label: 'Millimètre (mm)' },
      { value: 'cm', label: 'Centimètre (cm)' },
      { value: 'm', label: 'Mètre (m)' },
      { value: 'km', label: 'Kilomètre (km)' },
      { value: 'in', label: 'Pouce (in)' },
      { value: 'ft', label: 'Pied (ft)' },
    ],
    weight: [
      { value: 'g', label: 'Gramme (g)' },
      { value: 'kg', label: 'Kilogramme (kg)' },
      { value: 'lb', label: 'Livre (lb)' },
      { value: 'oz', label: 'Once (oz)' },
    ],
    temperature: [
      { value: 'C', label: 'Celsius (°C)' },
      { value: 'F', label: 'Fahrenheit (°F)' },
      { value: 'K', label: 'Kelvin (K)' },
    ],
    currency: [
      { value: 'EUR', label: 'Euro (€)' },
      { value: 'USD', label: 'Dollar US ($)' },
      { value: 'GBP', label: 'Livre Sterling (£)' },
      { value: 'JPY', label: 'Yen (¥)' },
    ],
  }

  return unitMap[moduleId] || []
}
</script>

<style scoped>
.dashboard {
  min-height: 100vh;
  background: #f8fafc;
  padding: 24px;
}

.dashboard-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 32px;
  padding-bottom: 16px;
  border-bottom: 1px solid #e2e8f0;
}

.dashboard-title {
  font-size: 24px;
  font-weight: 700;
  color: #1e293b;
  margin: 0;
}

.dashboard-controls {
  display: flex;
  align-items: center;
  gap: 12px;
}

.modules-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
  gap: 24px;
  max-width: 1400px;
  margin: 0 auto;
}

.module-card {
  min-height: 400px;
}

.module-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 24px;
  padding-bottom: 16px;
  border-bottom: 1px solid #e2e8f0;
}

.module-icon {
  font-size: 24px;
}

.module-header h3 {
  flex: 1;
  margin: 0;
  font-size: 18px;
  font-weight: 600;
}

.module-content {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.units-row {
  display: grid;
  grid-template-columns: 1fr auto 1fr;
  gap: 12px;
  align-items: center;
}

.result-box {
  background: #f0f9ff;
  border: 1px solid #0ea5e9;
  border-radius: 8px;
  padding: 16px;
  text-align: center;
}

.result-value {
  font-size: 24px;
  font-weight: 700;
  color: #0c4a6e;
}

.result-unit {
  font-size: 16px;
  font-weight: 500;
  color: #64748b;
  margin-left: 8px;
}

.result-formula {
  font-size: 14px;
  color: #64748b;
  font-style: italic;
  margin-top: 8px;
}

.error-box {
  margin-top: 8px;
}

@media (max-width: 768px) {
  .dashboard-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 16px;
  }

  .units-row {
    grid-template-columns: 1fr;
    gap: 16px;
  }
}
</style>
