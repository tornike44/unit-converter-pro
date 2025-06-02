<template>
  <div class="conversion-module" :style="{ borderColor: module.color }">
    <div class="module-header">
      <div class="module-title-section">
        <span class="module-icon">{{ module.icon }}</span>
        <h3 class="module-title">{{ module.title }}</h3>
      </div>

      <div class="module-actions">
        <el-button
          :type="module.isFavorite ? 'primary' : ''"
          @click="$emit('favoriteToggle')"
          size="small"
          circle
        >
          ⭐
        </el-button>
      </div>
    </div>

    <div class="module-content">
      <div class="conversion-form">
        <div class="input-group">
          <label class="input-label">Valeur</label>
          <el-input
            v-model.number="inputValue"
            @input="onValueChange"
            type="number"
            placeholder="Entrez une valeur"
            size="large"
            :disabled="isLoading"
          >
            <template #suffix>
              <span class="input-unit">{{ currentFromUnit }}</span>
            </template>
          </el-input>
        </div>

        <div class="units-section">
          <div class="unit-selector">
            <label class="input-label">De</label>
            <el-select
              v-model="currentFromUnit"
              @change="onUnitChange"
              placeholder="Unité source"
              size="large"
              :disabled="isLoading"
            >
              <el-option
                v-for="unit in getUnitsForType(module.type)"
                :key="unit.value"
                :label="unit.label"
                :value="unit.value"
              />
            </el-select>
          </div>

          <div class="conversion-arrow">
            <el-button @click="swapUnits" :disabled="isLoading" circle size="small"> ⇄ </el-button>
          </div>

          <div class="unit-selector">
            <label class="input-label">Vers</label>
            <el-select
              v-model="currentToUnit"
              @change="onUnitChange"
              placeholder="Unité cible"
              size="large"
              :disabled="isLoading"
            >
              <el-option
                v-for="unit in getUnitsForType(module.type)"
                :key="unit.value"
                :label="unit.label"
                :value="unit.value"
              />
            </el-select>
          </div>
        </div>

        <div class="convert-section">
          <el-button
            @click="performConversion"
            type="primary"
            size="large"
            :loading="isLoading"
            :disabled="!canConvert"
            block
          >
            {{ isLoading ? 'Conversion...' : 'Convertir' }}
          </el-button>
        </div>
      </div>

      <div v-if="result" class="result-section">
        <div class="result-card">
          <div class="result-value">
            {{ formatResult(result.convertedValue) }}
            <span class="result-unit">{{ result.convertedUnit }}</span>
          </div>

          <div v-if="result.formula" class="result-formula">
            <small>{{ result.formula }}</small>
          </div>
        </div>
      </div>

      <div v-if="error" class="error-section">
        <el-alert :title="error" type="error" :closable="false" show-icon />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, ref, watch } from 'vue'
import { apiService } from '@/services/api'
import type { ConversionModule, ModuleState, ConversionResult, ConversionType } from '@/types'

// Props et émissions
interface Props {
  module: ConversionModule
  moduleState?: ModuleState
}

const props = defineProps<Props>()

const emit = defineEmits<{
  favoriteToggle: []
  stateUpdate: [updates: Partial<ModuleState>]
}>()

// État local réactif
const inputValue = ref<number | null>(null)
const currentFromUnit = ref('')
const currentToUnit = ref('')

// Computed
const isLoading = computed(() => props.moduleState?.isLoading || false)
const result = computed(() => props.moduleState?.result)
const error = computed(() => props.moduleState?.error)

const canConvert = computed(() => {
  return (
    inputValue.value !== null &&
    inputValue.value > 0 &&
    currentFromUnit.value &&
    currentToUnit.value &&
    currentFromUnit.value !== currentToUnit.value
  )
})

// Synchronisation avec le store
watch(
  () => props.moduleState,
  (newState) => {
    if (newState) {
      inputValue.value = newState.value
      currentFromUnit.value = newState.fromUnit
      currentToUnit.value = newState.toUnit
    }
  },
  { immediate: true },
)

// Méthodes
const onValueChange = () => {
  emit('stateUpdate', { value: inputValue.value })
}

const onUnitChange = () => {
  emit('stateUpdate', {
    fromUnit: currentFromUnit.value,
    toUnit: currentToUnit.value,
  })
}

const swapUnits = () => {
  const temp = currentFromUnit.value
  currentFromUnit.value = currentToUnit.value
  currentToUnit.value = temp
  onUnitChange()
}

const performConversion = async () => {
  if (!canConvert.value) return

  emit('stateUpdate', { isLoading: true, error: null })

  try {
    const request = apiService.createConversionRequest(
      inputValue.value!,
      currentFromUnit.value,
      currentToUnit.value,
      props.module.type,
    )

    const result = await apiService.convert(request)

    emit('stateUpdate', {
      result,
      isLoading: false,
      error: null,
    })
  } catch (err) {
    const errorMessage = err instanceof Error ? err.message : 'Erreur de conversion'
    emit('stateUpdate', {
      isLoading: false,
      error: errorMessage,
      result: null,
    })
  }
}

const formatResult = (value: number): string => {
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

// Données des unités (à terme, cela pourrait venir d'un service)
const getUnitsForType = (type: ConversionType) => {
  const unitMap = {
    DISTANCE: [
      { value: 'mm', label: 'Millimètre (mm)' },
      { value: 'cm', label: 'Centimètre (cm)' },
      { value: 'm', label: 'Mètre (m)' },
      { value: 'km', label: 'Kilomètre (km)' },
      { value: 'in', label: 'Pouce (in)' },
      { value: 'ft', label: 'Pied (ft)' },
      { value: 'yd', label: 'Yard (yd)' },
      { value: 'mi', label: 'Mile (mi)' },
    ],
    WEIGHT: [
      { value: 'mg', label: 'Milligramme (mg)' },
      { value: 'g', label: 'Gramme (g)' },
      { value: 'kg', label: 'Kilogramme (kg)' },
      { value: 'oz', label: 'Once (oz)' },
      { value: 'lb', label: 'Livre (lb)' },
      { value: 'st', label: 'Stone (st)' },
      { value: 'ton', label: 'Tonne (ton)' },
    ],
    TEMPERATURE: [
      { value: 'C', label: 'Celsius (°C)' },
      { value: 'F', label: 'Fahrenheit (°F)' },
      { value: 'K', label: 'Kelvin (K)' },
    ],
    CURRENCY: [
      { value: 'EUR', label: 'Euro (€)' },
      { value: 'USD', label: 'Dollar US ($)' },
      { value: 'GBP', label: 'Livre Sterling (£)' },
      { value: 'JPY', label: 'Yen (¥)' },
      { value: 'CHF', label: 'Franc Suisse (CHF)' },
      { value: 'CAD', label: 'Dollar Canadien (CAD)' },
    ],
    VOLUME: [
      { value: 'ml', label: 'Millilitre (ml)' },
      { value: 'l', label: 'Litre (l)' },
      { value: 'fl_oz', label: 'Once liquide (fl oz)' },
      { value: 'cup', label: 'Tasse (cup)' },
      { value: 'pint', label: 'Pinte (pint)' },
      { value: 'gallon', label: 'Gallon (gallon)' },
    ],
    TIME: [
      { value: 'ms', label: 'Milliseconde (ms)' },
      { value: 's', label: 'Seconde (s)' },
      { value: 'min', label: 'Minute (min)' },
      { value: 'h', label: 'Heure (h)' },
      { value: 'day', label: 'Jour (day)' },
      { value: 'week', label: 'Semaine (week)' },
      { value: 'month', label: 'Mois (month)' },
      { value: 'year', label: 'Année (year)' },
    ],
    AREA: [
      { value: 'mm²', label: 'Millimètre carré (mm²)' },
      { value: 'cm²', label: 'Centimètre carré (cm²)' },
      { value: 'm²', label: 'Mètre carré (m²)' },
      { value: 'km²', label: 'Kilomètre carré (km²)' },
      { value: 'in²', label: 'Pouce carré (in²)' },
      { value: 'ft²', label: 'Pied carré (ft²)' },
      { value: 'yd²', label: 'Yard carré (yd²)' },
      { value: 'mi²', label: 'Mile carré (mi²)' },
    ],
  }

  return unitMap[type] || []
}
</script>

<style scoped lang="scss">
.conversion-module {
  @include card-base;
  border-left: 4px solid var(--module-color, #{$primary});
  min-height: 400px;
}

.module-header {
  @include flex-between;
  margin-bottom: $space-6;
  padding-bottom: $space-4;
  border-bottom: 1px solid $gray-200;
}

.module-title-section {
  @include flex-center;
  gap: $space-3;
}

.module-icon {
  font-size: $text-2xl;
}

.module-title {
  font-size: $text-lg;
  font-weight: 600;
  color: $gray-900;
  margin: 0;
}

.conversion-form {
  display: flex;
  flex-direction: column;
  gap: $space-5;
}

.input-group {
  display: flex;
  flex-direction: column;
  gap: $space-2;
}

.input-label {
  font-size: $text-sm;
  font-weight: 500;
  color: $gray-700;
}

.input-unit {
  color: $gray-500;
  font-size: $text-sm;
}

.units-section {
  display: grid;
  grid-template-columns: 1fr auto 1fr;
  gap: $space-3;
  align-items: end;
}

.unit-selector {
  display: flex;
  flex-direction: column;
  gap: $space-2;
}

.conversion-arrow {
  @include flex-center;
  padding-top: $space-6;
}

.convert-section {
  margin-top: $space-2;
}

.result-section {
  margin-top: $space-6;
  padding-top: $space-6;
  border-top: 1px solid $gray-200;
}

.result-card {
  @include flex-column;
  align-items: center;
  gap: $space-2;
  padding: $space-4;
  background: $gray-50;
  border-radius: $radius-lg;
  text-align: center;
}

.result-value {
  font-size: $text-2xl;
  font-weight: 700;
  color: var(--module-color, #{$primary});
}

.result-unit {
  font-size: $text-lg;
  font-weight: 500;
  color: $gray-600;
  margin-left: $space-2;
}

.result-formula {
  color: $gray-500;
  font-style: italic;
}

.error-section {
  margin-top: $space-4;
}

// Responsive
@include mobile-only {
  .units-section {
    grid-template-columns: 1fr;
    gap: $space-4;
  }

  .conversion-arrow {
    order: 3;
    padding-top: 0;
  }
}
</style>
