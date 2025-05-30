<script setup lang="ts">
import { ConversionType } from '@/types'

const modules = [
  {
    id: '1',
    type: ConversionType.DISTANCE,
    title: 'Distance',
    description: 'Convert lengths and distances',
    icon: '📏',
  },
  {
    id: '2',
    type: ConversionType.WEIGHT,
    title: 'Weight',
    description: 'Convert masses and weights',
    icon: '⚖️',
  },
  {
    id: '3',
    type: ConversionType.TEMPERATURE,
    title: 'Temperature',
    description: 'Celsius, Fahrenheit, Kelvin',
    icon: '🌡️',
  },
  {
    id: '4',
    type: ConversionType.CURRENCY,
    title: 'Currency',
    description: 'Real-time exchange rates',
    icon: '💰',
  },
  {
    id: '5',
    type: ConversionType.VOLUME,
    title: 'Volume',
    description: 'Liters, gallons, milliliters',
    icon: '🧪',
  },
  {
    id: '6',
    type: ConversionType.TIME,
    title: 'Time',
    description: 'Seconds, minutes, hours',
    icon: '⏰',
  },
  {
    id: '7',
    type: ConversionType.AREA,
    title: 'Area',
    description: 'Square meters, hectares',
    icon: '📐',
  },
]

const selectModule = (module: (typeof modules)[0]) => {
  console.log(`Selected module: ${module.title}`, module.type)
}
</script>

<template>
  <div class="dashboard">
    <div class="dashboard-container">
      <header class="dashboard-header">
        <h1 class="dashboard-title">Unit Converter</h1>
        <p class="dashboard-subtitle">Convert between different units of measurement</p>
      </header>

      <div class="modules-grid">
        <div
          v-for="module in modules"
          :key="module.id"
          class="module-card"
          @click="selectModule(module)"
        >
          <div class="module-icon">
            {{ module.icon }}
          </div>
          <h3 class="module-title">{{ module.title }}</h3>
          <p class="module-description">{{ module.description }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.dashboard {
  min-height: 100vh;
  background-color: #f8fafc;
  padding: 1rem;
}

@media (min-width: 768px) {
  .dashboard {
    padding: 1.5rem;
  }
}

@media (min-width: 1024px) {
  .dashboard {
    padding: 2rem;
  }
}

.dashboard-container {
  margin: 0 auto;
  max-width: 1280px;
}

.dashboard-header {
  margin-bottom: 2rem;
}

.dashboard-title {
  font-size: 1.5rem;
  font-weight: 700;
  color: #111827;
  margin-bottom: 0.5rem;
}

@media (min-width: 640px) {
  .dashboard-title {
    font-size: 1.875rem;
  }
}

.dashboard-subtitle {
  margin-top: 0.5rem;
  color: #4b5563;
  margin-bottom: 0;
}

.modules-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: 1rem;
}

@media (min-width: 640px) {
  .modules-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (min-width: 1024px) {
  .modules-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}

@media (min-width: 1280px) {
  .modules-grid {
    grid-template-columns: repeat(4, 1fr);
  }
}

.module-card {
  display: flex;
  height: 100%;
  cursor: pointer;
  flex-direction: column;
  border-radius: 0.5rem;
  border: 1px solid #e5e7eb;
  background-color: white;
  padding: 1.5rem;
  box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.05);
  transition: all 0.2s ease;
}

.module-card:hover {
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
  ring: 1px solid #e5e7eb;
}

.module-icon {
  margin-bottom: 1rem;
  display: flex;
  height: 3rem;
  width: 3rem;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  background-color: #f3f4f6;
  color: #374151;
  transition: all 0.2s ease;
  font-size: 1.5rem;
}

.module-card:hover .module-icon {
  background-color: #374151;
  color: white;
}

.module-title {
  margin-bottom: 0.5rem;
  font-size: 1.125rem;
  font-weight: 500;
  color: #111827;
}

.module-description {
  font-size: 0.875rem;
  color: #4b5563;
  margin: 0;
}
</style>
