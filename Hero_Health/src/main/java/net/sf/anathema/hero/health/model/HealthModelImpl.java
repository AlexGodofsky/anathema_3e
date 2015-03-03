package net.sf.anathema.hero.health.model;

import net.sf.anathema.charm.data.Duration;
import net.sf.anathema.hero.environment.HeroEnvironment;
import net.sf.anathema.hero.health.template.HealthTemplate;
import net.sf.anathema.hero.individual.change.ChangeAnnouncer;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.traits.model.TraitMap;
import net.sf.anathema.hero.traits.model.TraitModelFetcher;
import net.sf.anathema.hero.traits.model.types.AttributeType;
import net.sf.anathema.library.identifier.Identifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HealthModelImpl implements HealthModel {

  private final List<IHealthLevelProvider> healthLevelProviders = new ArrayList<>();
  private final List<IPainToleranceProvider> painResistanceProviders = new ArrayList<>();
  private final List<HealingTypeProvider> healingTypeProviders = new ArrayList<>();
  private final HealthTemplate healthTemplate;
  private HealingDataTable healingTimes;

  public HealthModelImpl(HealthTemplate healthTemplate) {
    this.healthTemplate = healthTemplate;
  }

  @Override
  public Identifier getId() {
    return ID;
  }

  @Override
  public void initialize(HeroEnvironment environment, Hero hero) {
    addHealthLevelProvider(new DyingStaminaHealthLevelProvider(TraitModelFetcher.fetch(hero)));
    addHealingTypeProvider(new DefaultHealingTypeProvider(hero));
    healingTimes = new HealingDataTable(environment.getResources());
  }

  @Override
  public void initializeListening(ChangeAnnouncer changeAnnouncer) {
    // nothing to do
  }

  @Override
  public void addHealthLevelProvider(IHealthLevelProvider provider) {
    healthLevelProviders.add(provider);
  }

  @Override
  public void addPainToleranceProvider(IPainToleranceProvider provider) {
    painResistanceProviders.add(provider);
  }
  
  @Override
  public void addHealingTypeProvider(HealingTypeProvider provider) {
  	healingTypeProviders.add(provider);
  }

  @Override
  public int getHealthLevelTypeCount(HealthLevelType type) {
    int typeCount = getBasicHealthLevel(type);
    for (IHealthLevelProvider provider : healthLevelProviders) {
      typeCount += provider.getHealthLevelTypeCount(type);
    }
    return typeCount;
  }

  @Override
  public int getBasicHealthLevel(HealthLevelType type) {
    return Collections.frequency(healthTemplate.levels, type);
  }

  @Override
  public int getPainToleranceLevel() {
    int painToleranceLevel = 0;
    for (IPainToleranceProvider provider : painResistanceProviders) {
      painToleranceLevel = Math.max(painToleranceLevel, provider.getPainToleranceLevel());
    }
    return painToleranceLevel;
  }
  
  @Override
	public Duration getHealingTime(HealthLevelType level, HealthType damage) {
		return healingTimes.getHealingTime(usesExaltedHealing(), level, damage);
	}
  
  private boolean usesExaltedHealing() {
  	boolean usesExaltedHealing = false;
  	for (HealingTypeProvider provider : healingTypeProviders) {
  		usesExaltedHealing |= provider.usesExaltedHealing();
  	}
  	return usesExaltedHealing;
  }

  private static class DyingStaminaHealthLevelProvider implements IHealthLevelProvider {
    private final TraitMap traits;

    public DyingStaminaHealthLevelProvider(TraitMap config) {
      this.traits = config;
    }

    @Override
    public int getHealthLevelTypeCount(HealthLevelType type) {
      if (type == HealthLevelType.DYING) {
        return traits.getTrait(AttributeType.Stamina).getCurrentValue();
      }
      return 0;
    }
  }
}