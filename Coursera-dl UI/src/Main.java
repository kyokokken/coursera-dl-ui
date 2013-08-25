/**
 * Last Update: 2013/07/18
 * 
 * 2013/07/18 12.58
 * TODO: Fare in modo che il cmd starta dalla cartella coursera, altrimenti mi scarica le cose
 * da un'altra parte.
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Enumeration;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Main extends JFrame implements Serializable, WindowListener {

	private static final long serialVersionUID = 1L;

	private Main main;

	private JList lstTasks;

	private JButton btnRemoveTask;

	public Main() {
		setTitle("coursera-dl ui");
		main = this;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblTasks = new JLabel("Tasks");
		pnlMain.add(lblTasks);

		lstTasks = new JList();
		lstTasks.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				ListSelectionModel lstSelModel = ((JList)(event
						.getSource())).getSelectionModel();
				if (!lstSelModel.isSelectionEmpty()) {
					btnRemoveTask.setEnabled(true);
				}
				else
				{
					btnRemoveTask.setEnabled(false);
				}
			}
		});
		lstTasks.setPreferredSize(new Dimension(100, 100));
		lstTasks.setModel(new DefaultListModel<String>() {
		});
		pnlMain.add(lstTasks);

		JButton btnAddTask = new JButton("Add Task");
		btnAddTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddTaskDialog addTaskDlg = new AddTaskDialog(main);
			}
		});
		pnlMain.add(btnAddTask);

		btnRemoveTask = new JButton("Remove Task");
		btnRemoveTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel model = (DefaultListModel)lstTasks.getModel();
				int selectedIndex = lstTasks.getSelectedIndex();
				model.remove(selectedIndex);
			}
		});
		btnRemoveTask.setEnabled(false);
		pnlMain.add(btnRemoveTask);

		JButton btnKillTask = new JButton("Kill Task");
		btnKillTask.setEnabled(false);
		pnlMain.add(btnKillTask);

		JButton btnExecuteTasks = new JButton("Execute Tasks");
		btnExecuteTasks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StringBuilder process = new StringBuilder();
				process.append("cmd.exe /k start python.exe E:\\coursera-master\\coursera\\coursera_dl.py -u jaajaa2@gmail.com");

				for (Enumeration<String> e = ((DefaultListModel<String>) lstTasks
						.getModel()).elements(); e.hasMoreElements();) {
					process.append(" " + e.nextElement());
				}

				try {
					System.out.println(process.toString());
					Runtime.getRuntime().exec(process.toString());

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
		pnlMain.add(btnExecuteTasks);
	}

	public static void main(String args[]) {
		Main main = new Main();
		main.start();
	}

	private void start() {
		pack();
		setVisible(true);
		this.addWindowListener(this);
		loadList();

	}

	public void addItemInTasksList(String courseName) {
		((DefaultListModel<String>) lstTasks.getModel()).addElement(courseName);
	}

	public void loadList() {
		DefaultListModel<String> listModel = new DefaultListModel<>();
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream("list.ser");
			ois = new ObjectInputStream(fis);
			Object listObject = ois.readObject();
			// lstTasks = (JList) listObject;
			listModel = (DefaultListModel) listObject;
			lstTasks.setModel(listModel);
			System.out.println("Load complete.");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
				ois.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void saveList() {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream("list.ser");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(lstTasks.getModel());
		} catch (Exception e) {

		} finally {
			try {
				fos.close();
				oos.close();
			} catch (IOException e) {

			}
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		saveList();
		System.out.println("Window closing.");
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}
}