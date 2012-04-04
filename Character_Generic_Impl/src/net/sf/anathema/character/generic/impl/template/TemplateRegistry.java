package net.sf.anathema.character.generic.impl.template;

import java.util.ArrayList;
import java.util.List;

import net.sf.anathema.character.generic.impl.rules.ExaltedEdition;
import net.sf.anathema.character.generic.rules.IExaltedEdition;
import net.sf.anathema.character.generic.template.ICharacterExternalsTemplate;
import net.sf.anathema.character.generic.template.ICharacterTemplate;
import net.sf.anathema.character.generic.template.ITemplateRegistry;
import net.sf.anathema.character.generic.template.ITemplateType;
import net.sf.anathema.character.generic.template.TemplateType;
import net.sf.anathema.character.generic.type.ICharacterType;
import net.sf.anathema.lib.collection.Table;

public class TemplateRegistry implements ITemplateRegistry {

  private final Table<ITemplateType, IExaltedEdition, ICharacterTemplate> table = new Table<ITemplateType, IExaltedEdition, ICharacterTemplate>();

  @Override
  public ICharacterExternalsTemplate[] getAllSupportedTemplates(ICharacterType type) {
    List<ICharacterTemplate> typeTemplates = new ArrayList<ICharacterTemplate>();
    for (ITemplateType templateType : table.getPrimaryKeys()) {
      if (templateType.getCharacterType().equals(type)) {
        for (IExaltedEdition edition : ExaltedEdition.values()) {
          ICharacterTemplate template = getTemplate(templateType, edition);
          if (template != null) {
            typeTemplates.add(template);
          }
        }
      }
    }
    return typeTemplates.toArray(new ICharacterTemplate[typeTemplates.size()]);
  }

  @Override
  public ICharacterTemplate getDefaultTemplate(ICharacterType type, IExaltedEdition edition) {
    ITemplateType templateType = new TemplateType(type);
    return getTemplate(templateType, edition);
  }

  @Override
  public ICharacterTemplate getTemplate(ICharacterExternalsTemplate template) {
    return table.get(template.getTemplateType(), template.getEdition());
  }

  @Override
  public ICharacterTemplate getTemplate(ITemplateType type, IExaltedEdition edition) {
    return table.get(type, edition);
  }

  @Override
  public void register(ICharacterTemplate template) {
    table.add(template.getTemplateType(), template.getEdition(), template);
  }
}