package fwcd.macromaker.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import fwcd.fructose.swing.DrawGraphicsButton;
import fwcd.fructose.swing.SizedLabel;
import fwcd.fructose.swing.View;

public class MacroMakerView implements View {
	private final JComponent view;
	
	private final JButton recordButton;
	private final JButton stopButton;
	private final JButton playButton;
	private final JLabel statusLabel;
	private final JProgressBar progressBar;
	private final JSpinner repeatsSpinner;
	
	public MacroMakerView(MacroMakerResponder responder) {
		view = new JPanel();
		view.setLayout(new BorderLayout());
		
		JPanel toolBar = new JPanel();
		
		recordButton = new DrawGraphicsButton(new Dimension(25, 25), (g2d, canvasSize) -> {
			g2d.setColor(Color.RED);
			g2d.fillOval(5, 5, 15, 15);
		});
		recordButton.addActionListener(l -> responder.record());
		toolBar.add(recordButton);
		
		stopButton = new JButton("Stop");
		stopButton.addActionListener(l -> responder.stop());
		toolBar.add(stopButton);
		
		playButton = new JButton("Play");
		playButton.addActionListener(l -> responder.play());
		toolBar.add(playButton);
		
		SpinnerModel repeatsSpinnerModel = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
		repeatsSpinner = new JSpinner(repeatsSpinnerModel);
		repeatsSpinner.setPreferredSize(new Dimension(50, 20));
		toolBar.add(repeatsSpinner);
		toolBar.add(new JLabel("Repeats"));
		
		view.add(toolBar, BorderLayout.NORTH);
		
		statusLabel = new SizedLabel("Idling...", 18);
		statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		view.add(statusLabel, BorderLayout.CENTER);
		
		progressBar = new JProgressBar(0, 100);
		view.add(progressBar, BorderLayout.SOUTH);
	}

	public void updateProgress(double progress) {
		progressBar.setValue((int) (progress * 100));
		progressBar.repaint();
	}
	
	@Override
	public JComponent getComponent() {
		return view;
	}

	public int getRepeats() {
		return (int) repeatsSpinner.getModel().getValue();
	}

	public void setStatus(String status) {
		statusLabel.setText(status);
	}

	public void showMessage(String message) {
		JOptionPane.showMessageDialog(view, message);
	}
}
