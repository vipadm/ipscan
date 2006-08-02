/**
 * 
 */
package net.azib.ipscan.gui;

import net.azib.ipscan.config.Labels;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * The "Show IP Details" Window
 *
 * @author anton
 */
public class DetailsWindow {

	private ResultTable resultTable;
	private Shell shell;
		
	public DetailsWindow(ResultTable resultTable) {
		this.resultTable = resultTable;
		createShell(resultTable.getShell());
	}
	
	public void open() {
		shell.open();
		Display display = Display.getCurrent();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) 
				display.sleep();
		}
		shell.dispose();
	}

	/**
	 * This method initializes shell
	 */
	private void createShell(Shell parent) {
		shell = new Shell(parent, SWT.TOOL | SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
		shell.setText(Labels.getInstance().getString("title.details"));
		shell.setSize(new Point(300, 300));
		shell.setImage(parent.getImage());
		FillLayout fillLayout = new FillLayout();
		fillLayout.spacing = 3;
		fillLayout.marginHeight = 3;
		fillLayout.marginWidth = 3;
		shell.setLayout(fillLayout);
		
		Text detailsText = new Text(shell, SWT.BORDER | SWT.READ_ONLY | SWT.MULTI | SWT.V_SCROLL);
		detailsText.setText(resultTable.getIPDetails());
		detailsText.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
		detailsText.setTabs(32);
		
		detailsText.addListener(SWT.Traverse, new Listener() {
			public void handleEvent(Event e) {
				if (e.detail == SWT.TRAVERSE_RETURN) {
					shell.close();
					shell.dispose();
				}
			}
		});
	}
	
}
