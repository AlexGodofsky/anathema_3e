package net.sf.anathema.hero.charms.display.tooltip;

import net.sf.anathema.hero.magic.charm.Charm;
import net.sf.anathema.hero.charms.model.special.multilearn.AbstractMultiLearnableCharm;
import net.sf.anathema.hero.charms.model.special.multilearn.CharmTier;
import net.sf.anathema.hero.charms.model.special.multilearn.EssenceFixedMultiLearnableCharm;
import net.sf.anathema.hero.charms.model.special.ISpecialCharm;
import net.sf.anathema.hero.charms.model.special.multilearn.StaticMultiLearnableCharm;
import net.sf.anathema.hero.charms.model.special.multilearn.TieredMultiLearnableCharm;
import net.sf.anathema.hero.charms.model.special.multilearn.TraitDependentMultiLearnableCharm;
import net.sf.anathema.magic.Magic;
import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.hero.traits.model.types.OtherTraitType;
import net.sf.anathema.lib.gui.ConfigurableTooltip;
import net.sf.anathema.framework.environment.Resources;

public class SpecialCharmContributor implements MagicTooltipContributor {
  private Resources resources;

  public SpecialCharmContributor(Resources resources) {
    this.resources = resources;
  }

  @Override
  public void buildStringForMagic(ConfigurableTooltip tooltip, Magic magic, Object specialDetails) {
    if (magic instanceof Charm && specialDetails instanceof ISpecialCharm) {
      Charm charm = (Charm) magic;
      ISpecialCharm details = (ISpecialCharm) specialDetails;
      if (details instanceof AbstractMultiLearnableCharm) {
        String label = resources.getString("CharmTreeView.ToolTip.Repurchases");
        String repurchaseInfo = null;
        if (details instanceof StaticMultiLearnableCharm) {
          repurchaseInfo = printStaticLimit((StaticMultiLearnableCharm) details);
        }
        if (details instanceof EssenceFixedMultiLearnableCharm) {
          return;
        }
        if (details instanceof TraitDependentMultiLearnableCharm) {
          repurchaseInfo = printTraitLimit((TraitDependentMultiLearnableCharm) details);
        }
        if (details instanceof TieredMultiLearnableCharm) {
          repurchaseInfo = printTieredLimit(charm, (TieredMultiLearnableCharm) details);
        }
        tooltip.appendLine(label, repurchaseInfo);
      }
    }
  }

  private String printTieredLimit(Charm charm, TieredMultiLearnableCharm details) {
    StringBuilder builder = new StringBuilder();
    CharmTier[] tiers = details.getTiers();
    CharmTier first = tiers[0], second = tiers[1], last = tiers[tiers.length - 1];
    for (CharmTier tier : tiers) {
      if (tier == first) {
        continue;
      }
      if (tier == last && tier != second) {
        builder.append(resources.getString("CharmTreeView.ToolTip.Repurchases.And"));
        builder.append(ConfigurableTooltip.Space);
      }
      if (tier == second || tiers.length <= 3) {
        builder.append(resources.getString("Essence"));
        builder.append(ConfigurableTooltip.Space);
      }
      builder.append(tier.getRequirement(OtherTraitType.Essence));

      int traitRequirement = tier.getRequirement(charm.getPrerequisites().getPrimaryTraitType());
      if (traitRequirement > 0) {
        builder.append("/");
        if (tier == second || tiers.length <= 3) {
          builder.append(resources.getString(charm.getPrerequisites().getPrimaryTraitType().getId()));
          builder.append(ConfigurableTooltip.Space);
        }
        builder.append(traitRequirement);
      }
      if (tier != last) {
        builder.append(ConfigurableTooltip.CommaSpace);
      }
    }
    return builder.toString();
  }

  private String printTraitLimit(TraitDependentMultiLearnableCharm details) {
    StringBuilder builder = new StringBuilder();
    builder.append("(");
    TraitType traitType = details.getTraitType();
    builder.append(resources.getString(traitType.getId()));
    if (details.getModifier() != 0) {
      builder.append(details.getModifier());
    }
    builder.append(")");
    builder.append(ConfigurableTooltip.Space);
    builder.append(resources.getString("CharmTreeView.ToolTip.Repurchases.Times"));
    return builder.toString();
  }

  private String printStaticLimit(StaticMultiLearnableCharm details) {
    StringBuilder builder = new StringBuilder();
    builder.append(resources.getString("CharmTreeView.ToolTip.Repurchases.Static" + details.getAbsoluteLearnLimit()));
    return builder.toString();
  }
}