<template>
  <div class="container">
    <h1>Unit Converter - Test API</h1>

    <div class="test-section">
      <h2>Test de connexion avec le backend</h2>

      <el-button @click="testApiHealth" :loading="isTestingHealth"> Test Health Check </el-button>

      <el-button @click="testConversion" :loading="isTestingConversion">
        Test Conversion
      </el-button>

      <div v-if="healthResult" class="result-box success">
        <strong>Health Check:</strong> {{ healthResult }}
      </div>

      <div v-if="conversionResult" class="result-box success">
        <strong>Conversion Result:</strong>
        <pre>{{ JSON.stringify(conversionResult, null, 2) }}</pre>
      </div>

      <div v-if="error" class="result-box error"><strong>Erreur:</strong> {{ error }}</div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { apiService } from '@/services/api'
import { ConversionType } from '@/types'
import type { ConversionResult } from '@/types'

const isTestingHealth = ref(false)
const isTestingConversion = ref(false)
const healthResult = ref<string | null>(null)
const conversionResult = ref<ConversionResult | null>(null)
const error = ref<string | null>(null)

const clearResults = () => {
  healthResult.value = null
  conversionResult.value = null
  error.value = null
}

const testApiHealth = async () => {
  isTestingHealth.value = true
  clearResults()

  try {
    const result = await apiService.healthCheck()
    healthResult.value = result
  } catch (err) {
    error.value = err instanceof Error ? err.message : 'Erreur inconnue'
  } finally {
    isTestingHealth.value = false
  }
}

const testConversion = async () => {
  isTestingConversion.value = true
  clearResults()

  try {
    const request = apiService.createConversionRequest(100, 'km', 'm', ConversionType.DISTANCE)

    const result = await apiService.convert(request)
    conversionResult.value = result
  } catch (err) {
    error.value = err instanceof Error ? err.message : 'Erreur inconnue'
  } finally {
    isTestingConversion.value = false
  }
}
</script>

<style scoped>
.container {
  padding: 2rem;
  max-width: 800px;
  margin: 0 auto;
}

.test-section {
  margin-top: 2rem;
  padding: 1.5rem;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.result-box {
  margin-top: 1rem;
  padding: 1rem;
  border-radius: 4px;
}

.success {
  background: #f0f9ff;
  border: 1px solid #0ea5e9;
  color: #0c4a6e;
}

.error {
  background: #fef2f2;
  border: 1px solid #ef4444;
  color: #991b1b;
}

pre {
  margin-top: 0.5rem;
  font-size: 0.875rem;
  white-space: pre-wrap;
}

.el-button {
  margin-right: 1rem;
  margin-bottom: 1rem;
}
</style>
