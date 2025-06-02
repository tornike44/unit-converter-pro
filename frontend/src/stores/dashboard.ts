import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import type { ConversionModule, ConversionType, ModuleState } from '@/types'

export const useDashboardStore = defineStore('dashboard', () => {
  // État réactif des modules disponibles
  const availableModules = ref<ConversionModule[]>([
    {
      id: 'distance',
      type: 'DISTANCE' as ConversionType,
      title: 'Distance',
      icon: '📏',
      color: '#3b82f6',
      isActive: true,
      isFavorite: true,
    },
    {
      id: 'weight',
      type: 'WEIGHT' as ConversionType,
      title: 'Poids',
      icon: '⚖️',
      color: '#10b981',
      isActive: true,
      isFavorite: true,
    },
    {
      id: 'temperature',
      type: 'TEMPERATURE' as ConversionType,
      title: 'Température',
      icon: '🌡️',
      color: '#f59e0b',
      isActive: true,
      isFavorite: false,
    },
    {
      id: 'currency',
      type: 'CURRENCY' as ConversionType,
      title: 'Devise',
      icon: '💰',
      color: '#8b5cf6',
      isActive: true,
      isFavorite: true,
    },
    {
      id: 'volume',
      type: 'VOLUME' as ConversionType,
      title: 'Volume',
      icon: '🥤',
      color: '#06b6d4',
      isActive: true,
      isFavorite: false,
    },
    {
      id: 'time',
      type: 'TIME' as ConversionType,
      title: 'Temps',
      icon: '⏱️',
      color: '#ec4899',
      isActive: true,
      isFavorite: false,
    },
    {
      id: 'area',
      type: 'AREA' as ConversionType,
      title: 'Surface',
      icon: '📐',
      color: '#84cc16',
      isActive: true,
      isFavorite: false,
    },
  ])

  // État des modules individuels
  const moduleStates = ref<Record<string, ModuleState>>({})

  // Configuration du dashboard
  const dashboardConfig = ref({
    maxColumns: 3,
    enableDragDrop: true,
    showOnlyFavorites: false,
  })

  // Computed : modules actifs selon la configuration
  const activeModules = computed(() => {
    return availableModules.value.filter((module) => {
      if (dashboardConfig.value.showOnlyFavorites) {
        return module.isActive && module.isFavorite
      }
      return module.isActive
    })
  })

  // Computed : modules favoris uniquement
  const favoriteModules = computed(() => {
    return availableModules.value.filter((module) => module.isFavorite)
  })

  // Actions : Gestion des modules
  const toggleModuleFavorite = (moduleId: string) => {
    const module = availableModules.value.find((m) => m.id === moduleId)
    if (module) {
      module.isFavorite = !module.isFavorite
    }
  }

  const toggleModuleActive = (moduleId: string) => {
    const module = availableModules.value.find((m) => m.id === moduleId)
    if (module) {
      module.isActive = !module.isActive
    }
  }

  const reorderModules = (newOrder: ConversionModule[]) => {
    availableModules.value = newOrder
  }

  // Actions : Gestion de l'état des modules
  const initializeModuleState = (moduleId: string) => {
    if (!moduleStates.value[moduleId]) {
      moduleStates.value[moduleId] = {
        value: null,
        fromUnit: '',
        toUnit: '',
        result: null,
        isLoading: false,
        error: null,
      }
    }
  }

  const updateModuleState = (moduleId: string, updates: Partial<ModuleState>) => {
    if (moduleStates.value[moduleId]) {
      moduleStates.value[moduleId] = {
        ...moduleStates.value[moduleId],
        ...updates,
      }
    }
  }

  const clearModuleResult = (moduleId: string) => {
    if (moduleStates.value[moduleId]) {
      moduleStates.value[moduleId].result = null
      moduleStates.value[moduleId].error = null
    }
  }

  const clearAllResults = () => {
    Object.keys(moduleStates.value).forEach((moduleId) => {
      clearModuleResult(moduleId)
    })
  }

  // Actions : Configuration du dashboard
  const updateDashboardConfig = (updates: Partial<typeof dashboardConfig.value>) => {
    dashboardConfig.value = {
      ...dashboardConfig.value,
      ...updates,
    }
  }

  const toggleShowOnlyFavorites = () => {
    dashboardConfig.value.showOnlyFavorites = !dashboardConfig.value.showOnlyFavorites
  }

  // Getters utilitaires
  const getModuleById = (moduleId: string) => {
    return availableModules.value.find((m) => m.id === moduleId)
  }

  const getModuleState = (moduleId: string) => {
    return moduleStates.value[moduleId]
  }

  return {
    // État
    availableModules,
    moduleStates,
    dashboardConfig,

    // Computed
    activeModules,
    favoriteModules,

    // Actions
    toggleModuleFavorite,
    toggleModuleActive,
    reorderModules,
    initializeModuleState,
    updateModuleState,
    clearModuleResult,
    clearAllResults,
    updateDashboardConfig,
    toggleShowOnlyFavorites,

    // Getters
    getModuleById,
    getModuleState,
  }
})
