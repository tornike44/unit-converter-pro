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
            <el-input placeholder="Entrez une valeur" type="number" size="large" />

            <div class="units-row">
              <el-select placeholder="De" size="large">
                <el-option label="Mètre" value="m" />
                <el-option label="Kilomètre" value="km" />
              </el-select>

              <el-button circle size="small">⇄</el-button>

              <el-select placeholder="Vers" size="large">
                <el-option label="Mètre" value="m" />
                <el-option label="Kilomètre" value="km" />
              </el-select>
            </div>

            <el-button type="primary" size="large" block> Convertir </el-button>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const modules = ref([
  {
    id: 'distance',
    title: 'Distance',
    icon: '📏',
    color: '#3b82f6',
  },
  {
    id: 'weight',
    title: 'Poids',
    icon: '⚖️',
    color: '#10b981',
  },
  {
    id: 'temperature',
    title: 'Température',
    icon: '🌡️',
    color: '#f59e0b',
  },
  {
    id: 'currency',
    title: 'Devise',
    icon: '💰',
    color: '#8b5cf6',
  },
])
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
