package io.coupling.intellij.plugin;

import static com.intellij.ui.ScrollPaneFactory.createScrollPane;
import static javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS;

import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentFactory.SERVICE;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.JBUI.Borders;
import java.awt.BorderLayout;
import java.util.Optional;
import javax.swing.JPanel;
import javax.swing.JTable;

public class AnalysedClassesPanel extends JPanel {

  private final String name;
  private transient Optional<AnalysedClassesTableModel> tableModelRef = Optional.empty();

  AnalysedClassesPanel(final String name) {
    this.name = name;
  }

  public void addTo(final ToolWindow toolWindow) {
    final ContentFactory contentFactory = SERVICE.getInstance();
    final boolean isNotLockable = false;
    final Content projectContent = contentFactory.createContent(this, name, isNotLockable);
    toolWindow.getContentManager().addContent(projectContent);
    this.setLayout(new BorderLayout());
    this.setBorder(Borders.empty(5));
  }

  public AnalysedClassesTableModel getTableModel() {
    return tableModelRef.orElseGet(() -> {
      final AnalysedClassesTableModel tableModel = new AnalysedClassesTableModel();
      final JTable table = new JBTable(tableModel);
      table.getColumnModel().getColumn(0).setPreferredWidth(300);
      table.setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
      table.setAutoCreateRowSorter(true);
      this.add(createScrollPane(table), BorderLayout.WEST);
      tableModelRef = Optional.of(tableModel);
      return tableModel;
    });
  }
}
