import javax.swing.JDialog;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class AddTaskDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField txtCourseName;
	private String courseName;
	private Main main;
	
	public AddTaskDialog(final Main main)
	{
		this.main = main;
		setModal(true);
		
		JPanel pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		
		JLabel lblCourseName = new JLabel("Course Name:");
		pnlMain.add(lblCourseName);
		
		txtCourseName = new JTextField();
		txtCourseName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addItemToList(main);
			}
		});
		txtCourseName.setText("asd");
		pnlMain.add(txtCourseName);
		txtCourseName.setColumns(10);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				addItemToList(main);
			}

			
		});
		
		pnlMain.add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		pnlMain.add(btnCancel);
		
		pack();
		setVisible(true);
	}
	
	private void addItemToList(final Main main) {
		courseName = txtCourseName.getText();
		main.addItemInTasksList(courseName);
		dispose();
	}
	
}
