<script setup lang="ts">
import { ref } from 'vue';
import { ConversionType } from '@/types';

// State du dashboard
const modules = ref([
  {
    id: '1',
    type: ConversionType.DISTANCE,
    title: 'Distance',
    icon: 'Ruler',
    color: '#3b82f6',
  },
  {
    id: '2',
    type: ConversionType.WEIGHT,
    title: 'Poids',
    icon: 'Scale',
    color: '#10b981',
  },
  {
    id: '3',
    type: ConversionType.TEMPERATURE,
    title: 'Température',
    icon: 'Thermometer',
    color: '#f59e0b',
  },
  {
    id: '4',
    type: ConversionType.CURRENCY,
    title: 'Devise',
    icon: 'Money',
    color: '#8b5cf6',
  },
]);
</script>

<template>
  <div class="dashboard">
    <h1>Unit Converter Pro</h1>

    <el-row :gutter="24" class="modules-grid">
      <el-col
        v-for="module in modules"
        :key="module.id"
        :xs="24"
        :sm="12"
        :md="8"
        class="module-col"
      >
        <el-card class="module-card" shadow="hover">
          <div class="module-header">
            <div class="module-icon" :style="{ backgroundColor: module.color }">
              📏
            </div>
            <h3>{{ module.title }}</h3>
          </div>
          <p>Type: {{ module.type }}</p>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped>
.dashboard {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.modules-grid {
  margin-top: 30px;
}

.module-col {
  margin-bottom: 24px;
}

.module-card {
  height: 200px;
  cursor: pointer;
  transition: transform 0.2s;
}

.module-card:hover {
  transform: translateY(-5px);
}

.module-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 16px;
}

.module-icon {
  width: 40px;
  height: 40px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
}
</style>
