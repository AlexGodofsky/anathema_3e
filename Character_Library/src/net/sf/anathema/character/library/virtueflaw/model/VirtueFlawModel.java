package net.sf.anathema.character.library.virtueflaw.model;

import net.sf.anathema.character.generic.additionaltemplate.AbstractAdditionalModelAdapter;
import net.sf.anathema.character.generic.additionaltemplate.AdditionalModelType;
import net.sf.anathema.character.generic.framework.additionaltemplate.listening.DedicatedCharacterChangeAdapter;
import net.sf.anathema.character.generic.framework.additionaltemplate.listening.VirtueChangeListener;
import net.sf.anathema.character.generic.framework.additionaltemplate.model.ICharacterModelContext;
import net.sf.anathema.character.generic.template.additional.IAdditionalTemplate;
import net.sf.anathema.character.library.virtueflaw.presenter.IVirtueFlawModel;
import net.sf.anathema.lib.control.booleanvalue.IBooleanValueChangedListener;
import net.sf.anathema.lib.control.change.GlobalChangeAdapter;
import net.sf.anathema.lib.control.change.IChangeListener;

public abstract class VirtueFlawModel extends AbstractAdditionalModelAdapter implements IVirtueFlawModel {

  private final String templateId;
  private final IVirtueFlaw virtueFlaw;
  private final ICharacterModelContext context;

  public VirtueFlawModel(final ICharacterModelContext context, IAdditionalTemplate additionalTemplate) {
    this.context = context;
    this.templateId = additionalTemplate.getId();
    virtueFlaw = new VirtueFlaw(context);
  }

  @Override
  public boolean isVirtueFlawChangable() {
    return !getContext().getBasicCharacterContext().isExperienced();
  }

  @Override
  public IVirtueFlaw getVirtueFlaw() {
    return virtueFlaw;
  }

  @Override
  public String getTemplateId() {
    return templateId;
  }

  @Override
  public AdditionalModelType getAdditionalModelType() {
    return AdditionalModelType.Advantages;
  }

  @Override
  public void addChangeListener(IChangeListener listener) {
    virtueFlaw.addRootChangeListener(listener);
    virtueFlaw.getName().addTextChangedListener(new GlobalChangeAdapter<String>(listener));
  }

  @Override
  public void addVirtueChangeListener(VirtueChangeListener listener) {
    context.getCharacterListening().addChangeListener(listener);
  }

  protected ICharacterModelContext getContext() {
    return context;
  }

  @Override
  public void addVirtueFlawChangableListener(final IBooleanValueChangedListener listener) {
    getContext().getCharacterListening().addChangeListener(new DedicatedCharacterChangeAdapter() {
      @Override
      public void experiencedChanged(boolean experienced) {
        listener.valueChanged(isVirtueFlawChangable());
      }
    });
  }
}