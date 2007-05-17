package com.intellij.openapi.vcs.changes.committed;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.ChangeListColumn;
import com.intellij.openapi.vcs.CommittedChangesProvider;
import com.intellij.openapi.vcs.ProjectLevelVcsManager;
import com.intellij.openapi.vcs.AbstractVcs;

import javax.swing.*;

/**
 * @author yole
 */
public class SelectFilteringAction extends LabeledComboBoxAction {
  private final Project myProject;
  private CommittedChangesTreeBrowser myBrowser;

  public SelectFilteringAction(final Project project, final CommittedChangesTreeBrowser browser) {
    super("Filter by");
    myProject = project;
    myBrowser = browser;
  }

  protected ComboBoxModel createModel() {
    final DefaultComboBoxModel model = new DefaultComboBoxModel(new Object[]{
      ChangeListFilteringStrategy.NONE,
      new ColumnFilteringStrategy(ChangeListColumn.NAME, null),
      new StructureFilteringStrategy(myProject),
      new IncomingChangesFilteringStrategy(myProject)
    });
    final AbstractVcs[] vcss = ProjectLevelVcsManager.getInstance(myProject).getAllActiveVcss();
    for(AbstractVcs vcs: vcss) {
      final CommittedChangesProvider provider = vcs.getCommittedChangesProvider();
      if (provider != null) {
        for(ChangeListColumn column: provider.getColumns()) {
          if (ChangeListColumn.isCustom(column)) {
            model.addElement(new ColumnFilteringStrategy(column, provider.getClass()));
          }
        }
      }
    }
    return model;
  }

  protected void selectionChanged(final Object selection) {
    myBrowser.setFilteringStrategy((ChangeListFilteringStrategy)selection);
  }
}