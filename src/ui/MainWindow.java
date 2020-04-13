package ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class MainWindow {

	protected Shell shlKittypowerBenchmarking;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlKittypowerBenchmarking.open();
		shlKittypowerBenchmarking.layout();
		while (!shlKittypowerBenchmarking.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlKittypowerBenchmarking = new Shell();
		shlKittypowerBenchmarking.setSize(563, 438);
		shlKittypowerBenchmarking.setText("KittyPower Benchmarking");
		
		Button btnRun = new Button(shlKittypowerBenchmarking, SWT.NONE);
		
		btnRun.setFont(SWTResourceManager.getFont("Segoe UI", 20, SWT.NORMAL));
		btnRun.setBounds(158, 26, 205, 55);
		btnRun.setText("Run");
		
		Label lblInstructions = new Label(shlKittypowerBenchmarking, SWT.NONE);
		lblInstructions.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.NORMAL));
		lblInstructions.setAlignment(SWT.CENTER);
		lblInstructions.setBounds(65, 99, 426, 72);
		lblInstructions.setText("The tests will take between 2-15 min to complete.\r\nPlease be patient! :)");
		
		Label lblCPUScore = new Label(shlKittypowerBenchmarking, SWT.NONE);
		lblCPUScore.setFont(SWTResourceManager.getFont("Segoe UI", 18, SWT.NORMAL));
		lblCPUScore.setBounds(65, 230, 222, 36);
		lblCPUScore.setText("CPU Score*: ");
		
		Label lblComment = new Label(shlKittypowerBenchmarking, SWT.NONE);
		lblComment.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblComment.setBounds(246, 360, 291, 29);
		lblComment.setText("*The score is a value between 0 and 100.");

		btnRun.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					Run.run();
					lblCPUScore.setText("CPU Score*: " + Run.getcpuScore());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
}
