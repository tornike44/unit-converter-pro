<template>
    <div class="dashboard">
      <div class="dashboard-header">
        <h1 class="dashboard-title">Unit Converter Pro</h1>
        
        <div class="dashboard-controls">
          <el-button-group>
            <el-button 
              :type="dashboardConfig.showOnlyFavorites ? 'primary' : ''"
              @click="toggleShowOnlyFavorites"
              size="small"
            >
              <span class="control-icon">⭐</span>
              Favoris seulement
            </el-button>
            
            <el-button 
              @click="clearAllResults"
              size="small"
            >
              <span class="control-icon">🔄</span>
              Effacer tout
            </el-button>
          </el-buttongroup>
        </div>
      </div>
  
      <div class="modules-grid-container">
        <draggable 
          v-model="activeModulesList" 
          @change="onModulesReorder"
          :disabled="!dashboardConfig.enableDragDrop"
          item-key="id"
          class="modules-grid"
          :class="{ 'drag-disabled': !dashboardConfig.enableDragDrop }"
        >
          <template #item="{ element: module }">
            <div 
              class="module-wrapper"
              :style="{ '--module-color': module.color }"
            >
              <ConversionModule 
                :module="module"
                :module-state="getModuleState(module.id)"
                @favorite-toggle="toggleModuleFavorite(module.id)"
                @state-update="(updates) => updateModuleState(module.id, updates)"
              />
            </div>
          </template>
        </draggable>
      </div>
  
      <div v-if="activeModules.length === 0" class="empty-state">
        <div class="empty-state-content">
          <span class="empty-icon">📦</span>
          <h3>Aucun module actif</h3>
          <p>Activez des modules pour commencer les conversions</p>
          <el-button 
            @click="showModuleSelector = true"
            type="primary"
          >
            Choisir des modules
          </el-button>
        </div>
      </div>
  
      <!-- Modal de sélection des modules (à implémenter plus tard) -->
      <el-dialog 
        v-model="showModuleSelector"
        title="Gérer les modules"
        width="600px"
      >
        <div class="module-selector">
          <p>Sélecteur de modules à implémenter...</p>
        </div>
      </el-dialog>
    </div>
  </template>
  
  <script setup lang="ts">
  import { computed, ref, onMounted } from 'vue'
  import { useDashboardStore } from '@/stores/dashboard'
  import draggable from 'vuedraggable'
  import ConversionModule from './ConversionModule.vue'
  
  // Store et état local
  const dashboardStore = useDashboardStore()
  const showModuleSelector = ref(false)
  
  // Computed depuis le store
  const {
    activeModules,
    dashboardConfig,
    toggleShowOnlyFavorites,
    clearAllResults,
    toggleModuleFavorite,
    updateModuleState,
    getModuleState,
    reorderModules,
    initializeModuleState
  } = dashboardStore
  
  // Liste réactive pour le drag & drop
  const activeModulesList = computed({
    get: () => activeModules.value,
    set: (newOrder) => {
      reorderModules(newOrder)
    }
  })
  
  // Gestion du réordonnancement
  const onModulesReorder = (event: any) => {
    console.log('Modules réordonnés:', event)
  }
  
  // Initialisation
  onMounted(() => {
    // Initialise l'état de tous les modules actifs
    activeModules.value.forEach(module => {
      initializeModuleState(module.id)
    })
  })
  </script>
  
  <style scoped lang="scss">
  .dashboard {
    min-height: 100vh;
    background: $gray-50;
    padding: $space-6;
  }
  
  .dashboard-header {
    @include flex-between;
    margin-bottom: $space-8;
    padding-bottom: $space-4;
    border-bottom: 1px solid $gray-200;
  
    @include mobile-only {
      @include flex-column;
      align-items: flex-start;
      gap: $space-4;
    }
  }
  
  .dashboard-title {
    font-size: $text-3xl;
    font-weight: 700;
    color: $gray-900;
    margin: 0;
  }
  
  .dashboard-controls {
    @include flex-center;
    gap: $space-3;
  }
  
  .control-icon {
    margin-right: $space-2;
  }
  
  .modules-grid-container {
    max-width: 1400px;
    margin: 0 auto;
  }
  
  .modules-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
    gap: $space-6;
    
    @include tablet-up {
      grid-template-columns: repeat(2, 1fr);
    }
    
    @include desktop-up {
      grid-template-columns: repeat(3, 1fr);
    }
  
    &.drag-disabled {
      .module-wrapper {
        cursor: default;
      }
    }
  }
  
  .module-wrapper {
    position: relative;
    transition: transform 0.2s ease;
  
    &:hover {
      transform: translateY(-2px);
    }
  }
  
  .empty-state {
    @include flex-center;
    min-height: 400px;
    text-align: center;
  }
  
  .empty-state-content {
    max-width: 400px;
    padding: $space-8;
    background: white;
    border-radius: $radius-xl;
    box-shadow: $shadow-sm;
  }
  
  .empty-icon {
    font-size: 4rem;
    display: block;
    margin-bottom: $space-4;
  }
  
  .empty-state-content h3 {
    font-size: $text-xl;
    color: $gray-900;
    margin-bottom: $space-2;
  }
  
  .empty-state-content p {
    color: $gray-600;
    margin-bottom: $space-6;
  }
  
  .module-selector {
    padding: $space-4;
    text-align: center;
    color: $gray-600;
  }
  
  // Variables CSS personnalisées pour les couleurs des modules
  .module-wrapper {
    --module-color: #{$primary};
  }
  </style>