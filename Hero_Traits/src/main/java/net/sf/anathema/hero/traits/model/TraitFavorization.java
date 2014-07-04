package net.sf.anathema.hero.traits.model;

import net.sf.anathema.hero.elsewhere.concept.CasteType;
import net.sf.anathema.hero.elsewhere.concept.HeroConceptFetcher;
import net.sf.anathema.hero.individual.model.Hero;
import org.jmock.example.announcer.Announcer;

public class TraitFavorization implements ITraitFavorization {

  private FavorableState state;
  private final Announcer<IFavorableStateChangedListener> favorableStateControl = Announcer.to(IFavorableStateChangedListener.class);
  private final MappableTypeIncrementChecker<FavorableState> favoredIncrementChecker;
  private final Trait trait;
  private final CasteType[] castes;
  private final boolean isRequiredFavored;
  private final Hero hero;
  
  public TraitFavorization(Hero hero, CasteType[] castes, IncrementChecker favoredIncrementChecker, Trait trait, boolean isRequiredFavored) {
	  this(hero, castes, new MonoTypeIncrementChecker<FavorableState>(favoredIncrementChecker, FavorableState.Favored),
			  trait, isRequiredFavored);
  }

  public TraitFavorization(Hero hero, CasteType[] castes, MappableTypeIncrementChecker<FavorableState> favoredIncrementChecker, Trait trait, boolean isRequiredFavored) {
    this.hero = hero;
    this.castes = castes;
    this.favoredIncrementChecker = favoredIncrementChecker;
    this.trait = trait;
    this.isRequiredFavored = isRequiredFavored;
    this.state = isRequiredFavored ? FavorableState.Favored : FavorableState.Default;
  }

  @Override
  public final void setFavorableState(FavorableState state) {
    if (state == FavorableState.Caste && isRequiredFavored) {
      throw new IllegalStateException("Traits that are required to be favored must not be of any caste");
    }
    if (this.state == state && !favoredIncrementChecker.isValidIncrement(state, 1)) {
      return;
    }
    if (isRequiredFavored && state == FavorableState.Default) {
      state = FavorableState.Favored;
    }
    this.state = state;
    ensureMinimalValue();
    favorableStateControl.announce().favorableStateChanged(this.state);
  }

  private void ensureMinimalValue() {
    final int minimalValue = getMinimalValue();
    if (trait.getCurrentValue() < minimalValue) {
      trait.setCurrentValue(minimalValue);
    }
  }

  @Override
  public int getMinimalValue() {
    return this.state == FavorableState.Favored ? 1 : 0;
  }

  @Override
  public void setFavored(boolean favored) {
    if (isCaste() || isFavored() == favored) {
      return;
    }
    setFavorableState(favored ? FavorableState.Favored : FavorableState.Default);
  }

  @SuppressWarnings("ConstantConditions")
  public void setCaste(boolean caste) {
    if (!caste && !isCaste()) {
      return;
    }
    setFavorableState(caste ? FavorableState.Caste : (isCaste() ? FavorableState.Default : FavorableState.Favored));
  }

  @Override
  public final FavorableState getFavorableState() {
    return state;
  }

  @Override
  public final void addFavorableStateChangedListener(IFavorableStateChangedListener listener) {
    favorableStateControl.addListener(listener);
  }

  @Override
  public final boolean isFavored() {
    return state == FavorableState.Favored;
  }

  @Override
  public final boolean isCaste() {
    return state == FavorableState.Caste;
  }

  @Override
  public final boolean isCasteOrFavored() {
    return isCaste() || isFavored();
  }

  @Override
  public void updateFavorableStateToCaste() {
    CasteType casteType = HeroConceptFetcher.fetch(hero).getCaste().getType();
    setCaste(isSupportedCasteType(casteType));
  }

  private boolean isSupportedCasteType(CasteType casteType) {
    for (CasteType caste : castes) {
      if (caste == casteType) {
        return true;
      }
    }
    return false;
  }
}