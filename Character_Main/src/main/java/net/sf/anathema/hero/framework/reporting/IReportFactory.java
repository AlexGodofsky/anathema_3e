package net.sf.anathema.hero.framework.reporting;

import net.sf.anathema.framework.IApplicationModel;
import net.sf.anathema.framework.environment.Environment;

public interface IReportFactory {

  Report[] createReport(Environment environment, IApplicationModel model);
}
