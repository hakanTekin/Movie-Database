import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.text.AttributedCharacterIterator;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.RepaintManager;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class MenuBar {

	private Font textFont = new Font("Century Gothic", Font.PLAIN, 20);
	private Font uiFont = new Font("Century Gothic", Font.BOLD, 15);
	// JFrame
	private JFrame frame;
	// MenuBar
	private JMenuBar menuBar;
	// Main Menus
	private JMenu manageMenu;
	private JMenu search;
	private JMenu formConnection;
	// Sub Menus
	private JMenu addElement;
	private JMenu removeElement;
	private JMenu updateElement;

	private JMenu withMovie;
	private JMenu withActors;
	private JMenu withDirector;

	// with comp

	private JMenuItem movieProject;
	private JMenuItem actorAward;
	private JMenuItem directorAward;

	// Search components
	private JMenuItem searchMovies;
	private JMenuItem searchActors;
	private JMenuItem searchDirectors;
	private JMenuItem searchStudios;
	private JMenuItem searchAwards;
	// Management Components
	private JMenuItem addDirector;
	private JMenuItem removeDirector;
	private JMenuItem updateDirector;
	private JMenuItem addMovie;
	private JMenuItem removeMovie;
	private JMenuItem updateMovie;
	private JMenuItem addActor;
	private JMenuItem removeActor;
	private JMenuItem updateActor;
	private JMenuItem addStudio;
	private JMenuItem removeStudio;
	private JMenuItem updateStudio;
	private JMenuItem addAward;
	private JMenuItem removeAward;
	private JMenuItem updateAward;
	private JMenuItem logIn;
	private JButton button;

	private JScrollPane resultPane;

	private JLabel label;

	Connection c;

	public MenuBar() {

		// launch jframe
		frame = new JFrame("TERMINAL");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuBar = new JMenuBar();

		button = new JButton("Log In!");
		button.setFont(textFont);
		button.setSize(100, 50);
		button.setLocation(614, 0);
		button.setBackground(Color.RED);
		// build the Edit menu
		manageMenu = new JMenu("Manage");
		logIn = new JMenuItem("Log in to manage database");
		manageMenu.add(logIn);

		// log in
		logInEvent e = new logInEvent();
		logIn.addActionListener(e);
		button.addActionListener(e);

		// build the search menu
		search = new JMenu("Search");
		searchMovies = new JMenuItem("for Movies...");
		searchActors = new JMenuItem("for Actors...");
		searchDirectors = new JMenuItem("for Directors...");
		searchAwards = new JMenuItem("for Awards...");
		searchStudios = new JMenuItem("for Studios...");
		search.add(searchMovies);
		search.add(searchActors);
		search.add(searchDirectors);
		search.add(searchAwards);
		search.add(searchStudios);
		searchStudio searchS = new searchStudio();
		searchActor searchA = new searchActor();
		searchAward searchAw = new searchAward();
		searchMovie searchM = new searchMovie();
		searchDirector searchD = new searchDirector();
		searchStudios.addActionListener(searchS);
		searchActors.addActionListener(searchA);
		searchAwards.addActionListener(searchAw);
		searchDirectors.addActionListener(searchD);
		searchMovies.addActionListener(searchM);

		// add menus to menubar
		menuBar.add(manageMenu);
		menuBar.add(search);

		// put the menubar on the frame
		frame.setJMenuBar(menuBar);

		// set the jframe
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(720, 600));
		// frame.setSize(720, 600);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

		// frame.setLayout(new FlowLayout(FlowLayout.RIGHT));
		resultPane = new JScrollPane();
		resultPane.setBackground(Color.RED);
		frame.setLayout(new GridLayout(1, 1));
		frame.setVisible(true);

		// set the Jlabel
		label = new JLabel("label");
		label.setText("Not Logged In");
		label.setSize(500, 100);
		label.setLocation(590, 470);
		label.setFont(textFont);
		label.setForeground(Color.black);

		// FONTS

		manageMenu.setFont(uiFont);
		search.setFont(uiFont);
		searchActors.setFont(uiFont);
		searchAwards.setFont(uiFont);
		searchDirectors.setFont(uiFont);
		searchMovies.setFont(uiFont);
		searchStudios.setFont(uiFont);
		logIn.setFont(uiFont);

		// there is no connection in the beginning
		c = Connector.CreateConnection("admin", "amk123"); // userame.gettext e cevir;

		// finally
		resultPane = TableCreator.createPanelWithResultSet(c, returnRandomTableStatement());
		if (resultPane != null)
			frame.add(resultPane);
		frame.validate();
	}

	public static String returnRandomTableStatement() {
		String statement = null;
		File f = new File("statements.txt");
		try {
			if (f.exists()) {

				Scanner sc = new Scanner(f);
				int lineCount = 0;

				while (sc.hasNextLine()) {

					sc.nextLine();
					lineCount++;
				}
				sc.close();
				sc = new Scanner(f);
				Random rd = new Random();

				int lineNum = rd.nextInt(lineCount);
				System.out.println(lineCount);
				System.out.println(lineNum);
				for (int i = 0; i < lineNum; i++)
					System.out.println(sc.nextLine());

				return sc.nextLine();

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	public void logIn() {

		// REMOVE BUTTON

		frame.remove(button);
		frame.repaint();
		// build the File menu

		manageMenu.remove(logIn);
		addElement = new JMenu("Add...");
		removeElement = new JMenu("Remove...");
		updateElement = new JMenu("Update...");
		formConnection = new JMenu("Form a connection...");
		withActors = new JMenu("...with an actor");
		withDirector = new JMenu("...with a director");
		withMovie = new JMenu("...with a movie");
		// FONTS

		addElement.setFont(textFont);
		removeElement.setFont(textFont);
		updateElement.setFont(textFont);

		// Sub menu comp

		movieProject = new JMenuItem("Create movie project");
		actorAward = new JMenuItem("Add actor award");
		directorAward = new JMenuItem("Add director award");

		addDirector = new JMenuItem("...a new director");
		removeDirector = new JMenuItem("...an existing director");
		updateDirector = new JMenuItem("...an existing director");

		addMovie = new JMenuItem("...a new movie");
		removeMovie = new JMenuItem("...an existing movie");
		updateMovie = new JMenuItem("...an existing movie");

		addActor = new JMenuItem("...a new actor");
		removeActor = new JMenuItem("...an existing actor");
		updateActor = new JMenuItem("...an existing actor");

		addStudio = new JMenuItem("...a new studio");
		removeStudio = new JMenuItem("...an existing studio");
		updateStudio = new JMenuItem("...an existing studio");

		addAward = new JMenuItem("...a new award");
		removeAward = new JMenuItem("...an existing award");
		updateAward = new JMenuItem("...an existing award");

		manageMenu.add(addElement);
		manageMenu.add(removeElement);
		manageMenu.add(updateElement);
		manageMenu.add(formConnection);

		addElement.add(addActor);
		addElement.add(addDirector);
		addElement.add(addMovie);
		addElement.add(addStudio);
		addElement.add(addAward);

		removeElement.add(removeActor);
		removeElement.add(removeDirector);
		removeElement.add(removeMovie);
		removeElement.add(removeStudio);
		removeElement.add(removeAward);

		updateElement.add(updateActor);
		updateElement.add(updateDirector);
		updateElement.add(updateMovie);
		updateElement.add(updateStudio);
		updateElement.add(updateAward);

		formConnection.add(withActors);
		formConnection.add(withDirector);
		formConnection.add(withMovie);

		withActors.add(actorAward);
		withMovie.add(movieProject);
		withDirector.add(directorAward);
		// FONTS

		addActor.setFont(uiFont);
		removeActor.setFont(uiFont);
		updateActor.setFont(uiFont);

		addDirector.setFont(uiFont);
		removeDirector.setFont(uiFont);
		updateDirector.setFont(uiFont);

		addMovie.setFont(uiFont);
		removeMovie.setFont(uiFont);
		updateMovie.setFont(uiFont);

		addStudio.setFont(uiFont);
		removeStudio.setFont(uiFont);
		updateStudio.setFont(uiFont);

		addAward.setFont(uiFont);
		removeAward.setFont(uiFont);
		updateAward.setFont(uiFont);

		// click events

		// DIRECTORS
		addDirector addD = new addDirector();
		addDirector.addActionListener(addD);

		removeDirector removeD = new removeDirector();
		removeDirector.addActionListener(removeD);

		updateDirector updateD = new updateDirector();
		updateDirector.addActionListener(updateD);

		// ACTORS

		addActor addA = new addActor();
		addActor.addActionListener(addA);

		removeActor removeA = new removeActor();
		removeActor.addActionListener(removeA);

		updateActor updateA = new updateActor();
		updateActor.addActionListener(updateA);

		// STUDIOS

		addStudio addS = new addStudio();
		addStudio.addActionListener(addS);

		removeStudio removeS = new removeStudio();
		removeStudio.addActionListener(removeS);

		updateStudio updateS = new updateStudio();
		updateStudio.addActionListener(updateS);

		// MOVIES

		addMovie addM = new addMovie();
		addMovie.addActionListener(addM);

		removeMovie removeM = new removeMovie();
		removeMovie.addActionListener(removeM);

		updateMovie updateM = new updateMovie();
		updateMovie.addActionListener(updateM);

		// AWARDS

		addAward addAW = new addAward();
		addAward.addActionListener(addAW);

		removeAward removeAW = new removeAward();
		removeAward.addActionListener(removeAW);

		updateAward updateAW = new updateAward();
		updateAward.addActionListener(updateAW);

		// CON

		createProject cp = new createProject();
		movieProject.addActionListener(cp);

	}

	public class logInEvent implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			JTextField username = new JTextField();
			JTextField password = new JPasswordField();
			Object[] message = { "Username:", username, "Password:", password };
			int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {

				c = Connector.CreateConnection("admin", "amk123"); // userame.gettext e cevir
				if (c != null) {
					logIn();
					JOptionPane.showMessageDialog(null, "Successfully logged in!");
					label.setText("Logged in!");
				} else {
					JOptionPane.showMessageDialog(null, "Invalid username or password!");
				}
			} else {
				// JOptionPane.showMessageDialog(null,"Login Cancelled!");
			}

		}

	}

	public class addDirector implements ActionListener {// done2

		public void actionPerformed(ActionEvent e) {
			JTextField directorName = new JTextField();
			Object[] message = { "Director Name:", directorName, };

			Statement st = null;
			try {
				st = c.createStatement();
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			int option = JOptionPane.showConfirmDialog(null, message, "New Director", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				if (directorName.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Director name cannot be empty!");
				}

				else {
					// add director here
					try {
						st.executeQuery(
								"INSERT INTO DIRECTOR VALUES(dr_id_creator.NEXTVAL,'" + directorName.getText() + "')");
						JOptionPane.showMessageDialog(null,
								directorName.getText() + " successfully added to the database!");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} else {

				// JOptionPane.showMessageDialog(null,"Adding Cancelled!");
			}

		}
	}

	public class removeDirector implements ActionListener {// done1

		public void actionPerformed(ActionEvent e) {

			Statement st = null;
			int directorID = -1;
			try {
				frame.remove(resultPane);
				resultPane = TableCreator.createPanelWithResultSet(c, "SELECT * FROM DIRECTOR");
				frame.add(resultPane);
				frame.validate();
				JTextField ID = new JTextField();
				Object[] message2 = { "ID you want to remove:", ID, };
				int deleteID = -1;
				int option2 = JOptionPane.showConfirmDialog(null, message2, "Delete Director",
						JOptionPane.OK_CANCEL_OPTION);
				if (option2 == JOptionPane.OK_OPTION) {
					if (ID.getText().equals("")) {

						JOptionPane.showMessageDialog(null, "ID Cannot be null!");
					}

					else {
						deleteID = Integer.parseInt(ID.getText());
						try {
							st = c.createStatement();
							st.executeQuery("DELETE FROM DIRECTOR WHERE DIRECTOR_ID = " + (deleteID));
							frame.remove(resultPane);
							resultPane = TableCreator.createPanelWithResultSet(c, "SELECT * FROM DIRECTOR");
							frame.add(resultPane);
							frame.validate();
						} catch (SQLIntegrityConstraintViolationException e2) {
							// TODO: handle exception
							st.executeQuery("DELETE FROM MOVIE_PROJECT WHERE DIRECTOR_ID = " + (deleteID));
							st.executeQuery("DELETE FROM DIRECTOR_AWARDS WHERE DIRECTOR_ID = " + (deleteID));
							st.executeQuery("DELETE FROM DIRECTOR WHERE DIRECTOR_ID = " + (deleteID));
							frame.remove(resultPane);
							resultPane = TableCreator.createPanelWithResultSet(c, "SELECT * FROM DIRECTOR");
							frame.add(resultPane);
							frame.validate();
						}
					}
				} else {

					// JOptionPane.showMessageDialog(null,"Adding Cancelled!");
				}

			} catch (Exception e2) {
				// TODO: handle exception

			}

		}

	}

	public class updateDirector implements ActionListener {// done1

		public void actionPerformed(ActionEvent e) {
			Statement st = null;
			int directorID = -1;
			try {
				frame.remove(resultPane);
				resultPane = TableCreator.createPanelWithResultSet(c, "SELECT * FROM DIRECTOR");
				frame.add(resultPane);
				frame.validate();
				frame.validate();
				JTextField ID = new JTextField();
				Object[] message2 = { "ID you want to update:", ID, };
				int updateID = -1;
				int option2 = JOptionPane.showConfirmDialog(null, message2, "Update Director",
						JOptionPane.OK_CANCEL_OPTION);
				if (option2 == JOptionPane.OK_OPTION) {
					if (ID.getText().equals("")) {

						JOptionPane.showMessageDialog(null, "ID Cannot be null!");
					}

					else {
						updateID = Integer.parseInt(ID.getText());
						try {
							st = c.createStatement();
							JTextField name = new JTextField();
							Object[] message3 = { "New Director Name:", name, };
							int option3 = JOptionPane.showConfirmDialog(null, message3, "Update Director",
									JOptionPane.OK_CANCEL_OPTION);
							if (option3 == JOptionPane.OK_OPTION) {
								if (name.getText().equals("")) {

									JOptionPane.showMessageDialog(null, "New name cannot be empty!");
								} else {
									st.executeQuery("UPDATE DIRECTOR SET NAME = '" + name.getText()
											+ "' WHERE DIRECTOR_ID = " + (updateID));
								}
							}
							frame.remove(resultPane);
							resultPane = TableCreator.createPanelWithResultSet(c, "SELECT * FROM DIRECTOR");
							frame.add(resultPane);
							frame.validate();
							frame.validate();
						} catch (Exception e2) {
							// TODO: handle exception
						}
					}
				} else {

					// JOptionPane.showMessageDialog(null,"Adding Cancelled!");
				}

			} catch (Exception e2) {
				// TODO: handle exception
			}
		}

	}

	public class addMovie implements ActionListener {// done2

		public void actionPerformed(ActionEvent e) {

			JTextField movieName = new JTextField();

			JTextField movieBudget = new JTextField();
			JTextField movieBoxOffice = new JTextField();
			JTextField movieMPAA = new JTextField();
			JTextField movieDate = new JTextField();
			JTextField movieRating = new JTextField();
			Object[] message = { "Movie Title:", movieName,

					"Budget:", movieBudget, "Box Office:", movieBoxOffice, "MPAA Rating:", movieMPAA,
					"Release Date (YYYY/MM/DD):", movieDate, "Rating:", movieRating

			};
			Statement st = null;
			int movieID = -1;
			try {
				st = c.createStatement();
				ResultSet rs = st.executeQuery("SELECT MAX(MOVIE_ID) FROM MOVIE");
				rs.next();
				movieID = rs.getInt(1);
				System.out.println(movieID);
			} catch (Exception e2) {
				// TODO: handle exception
			}
			int option = JOptionPane.showConfirmDialog(null, message, "New Movie", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				if (movieName.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Movie name cannot be empty!");
				} else {
					try {
						st.executeQuery("INSERT INTO MOVIE VALUES(m_id_creator.NEXTVAL,'" + movieName.getText()
								+ "',TO_DATE('" + movieDate.getText() + "', 'YYYY/MM/DD'),"
								+ Integer.parseInt(movieBoxOffice.getText()) + ",'" + movieMPAA.getText() + "',"
								+ Integer.parseInt(movieBudget.getText()) + ","
								+ Integer.parseInt(movieRating.getText()) + ")");

						JOptionPane.showMessageDialog(null,
								movieName.getText() + " successfully added to the database!");

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Something wrong has happened! Check your input!");
						e1.printStackTrace();
					}
				}

			} else {
				// JOptionPane.showMessageDialog(null,"Adding Cancelled!");
			}

		}

	}

	public class updateMovie implements ActionListener {// done1

		public void actionPerformed(ActionEvent e) {
			Statement st = null;
			int movieID = -1;
			try {
				frame.remove(resultPane);
				resultPane = TableCreator.createPanelWithResultSet(c, "SELECT * FROM MOVIE");
				frame.add(resultPane);
				frame.validate();
				frame.validate();

				JTextField ID = new JTextField();
				Object[] message2 = { "ID you want to update:", ID, };
				int updateID = -1;
				int option2 = JOptionPane.showConfirmDialog(null, message2, "Update Movie",
						JOptionPane.OK_CANCEL_OPTION);
				if (option2 == JOptionPane.OK_OPTION) {
					if (ID.getText().equals("")) {

						JOptionPane.showMessageDialog(null, "ID Cannot be null!");
					}

					else {
						updateID = Integer.parseInt(ID.getText());
						try {
							st = c.createStatement();
							JTextField movieName = new JTextField();

							JTextField movieBudget = new JTextField();
							JTextField movieBoxOffice = new JTextField();
							JTextField movieMPAA = new JTextField();
							JTextField movieDate = new JTextField();
							JTextField movieRating = new JTextField();
							Object[] message = { "Movie Title:", movieName,

									"Budget:", movieBudget, "Box Office:", movieBoxOffice, "MPAA Rating:", movieMPAA,
									"Release Date (YYYY/MM/DD):", movieDate, "Rating:", movieRating

							};
							int option3 = JOptionPane.showConfirmDialog(null, message, "Update Movie",
									JOptionPane.OK_CANCEL_OPTION);
							if (option3 == JOptionPane.OK_OPTION) {
								if (movieName.getText().equals("")) {

									JOptionPane.showMessageDialog(null, "New name cannot be empty!");
								} else {
									st.executeQuery("UPDATE MOVIE SET TITLE = '" + movieName.getText()
											+ "' WHERE MOVIE_ID = " + (updateID));
									st.executeQuery(
											"UPDATE MOVIE SET BUDGET = '" + Integer.parseInt(movieBudget.getText())
													+ "' WHERE MOVIE_ID = " + (updateID));
									st.executeQuery("UPDATE MOVIE SET BOX_OFFICE = '"
											+ Integer.parseInt(movieBoxOffice.getText()) + "' WHERE MOVIE_ID = "
											+ (updateID));
									st.executeQuery("UPDATE MOVIE SET RELEASE_DATE = TO_DATE('" + movieDate.getText()
											+ "','YYYY/MM/DD') WHERE MOVIE_ID = " + (updateID));
									st.executeQuery(
											"UPDATE MOVIE SET RATING = '" + Integer.parseInt(movieRating.getText())
													+ "' WHERE MOVIE_ID = " + (updateID));
								}
							}

							frame.remove(resultPane);
							resultPane = TableCreator.createPanelWithResultSet(c, "SELECT * FROM MOVIE");
							frame.add(resultPane);
							frame.validate();
							frame.validate();

						} catch (Exception e2) {
							// TODO: handle exception
							e2.printStackTrace();
						}
					}
				} else {

					// JOptionPane.showMessageDialog(null,"Adding Cancelled!");
				}

			} catch (Exception e2) {
				// TODO: handle exception
			}

		}

	}

	public class removeMovie implements ActionListener {// done1

		public void actionPerformed(ActionEvent e) {

			Statement st = null;
			int MOVIEID = -1;
			try {

				frame.remove(resultPane);
				resultPane = TableCreator.createPanelWithResultSet(c, "SELECT * FROM MOVIE");
				frame.add(resultPane);
				frame.validate();

				JTextField ID = new JTextField();
				Object[] message2 = { "ID you want to remove:", ID, };
				int deleteID = -1;
				int option2 = JOptionPane.showConfirmDialog(null, message2, "Delete MOVIE",
						JOptionPane.OK_CANCEL_OPTION);
				if (option2 == JOptionPane.OK_OPTION) {
					if (ID.getText().equals("")) {

						JOptionPane.showMessageDialog(null, "ID Cannot be null!");
					}

					else {
						deleteID = Integer.parseInt(ID.getText());
						try {
							st = c.createStatement();
							st.executeQuery("DELETE FROM MOVIE WHERE MOVIE_ID = " + (deleteID));
							frame.remove(resultPane);
							resultPane = TableCreator.createPanelWithResultSet(c, "SELECT * FROM MOVIE");
							frame.add(resultPane);
							frame.validate();

						} catch (SQLIntegrityConstraintViolationException e2) {
							// TODO: handle exception
							st.executeQuery("DELETE FROM MOVIE_PROJECT WHERE MOVIE_ID = " + (deleteID));
							st.executeQuery("DELETE FROM MOVIE_CAST WHERE MOVIE_ID = " + (deleteID));
							st.executeQuery("DELETE FROM MOVIE_AWARDS WHERE MOVIE_ID = " + (deleteID));
							st.executeQuery("DELETE FROM MOVIE WHERE MOVIE_ID = " + (deleteID));
							frame.remove(resultPane);
							resultPane = TableCreator.createPanelWithResultSet(c, "SELECT * FROM MOVIE");
							frame.add(resultPane);
							frame.validate();
						}
					}
				} else {

					// JOptionPane.showMessageDialog(null,"Adding Cancelled!");
				}

			} catch (Exception e2) {
				// TODO: handle exception
			}

		}

	}

	public class addActor implements ActionListener {// done2

		public void actionPerformed(ActionEvent e) {
			JTextField actorName = new JTextField();
			JTextField actorHeight = new JTextField();
			JTextField actorWeight = new JTextField();
			JTextField actorHair = new JTextField();
			Object[] message = { "Actor Name:", actorName, "Actor Height:", actorHeight, "Actor Weight:", actorWeight,
					"Actor Hair:", actorHair };
			Statement st = null;
			int actorID = -1;
			try {
				st = c.createStatement();
				ResultSet rs = st.executeQuery("SELECT MAX(ACTOR_ID) FROM ACTOR");
				rs.next();
				actorID = rs.getInt(1);
				System.out.println(actorID);
			} catch (Exception e2) {
				// TODO: handle exception
			}
			int option = JOptionPane.showConfirmDialog(null, message, "New Actor", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				if (actorName.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Actor name cannot be empty!");
				} else {
					// add director here
					try {
						st.executeQuery("INSERT INTO ACTOR VALUES(ac_id_creator.NEXTVAL,'" + actorName.getText() + "',"
								+ Integer.parseInt(actorHeight.getText()) + ","
								+ Integer.parseInt(actorWeight.getText()) + ",'" + actorHair.getText() + "')");
						JOptionPane.showMessageDialog(null,
								actorName.getText() + " successfully added to the database!");

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Something wrong has happened! Check your input!");
						e1.printStackTrace();
					}
				}

			} else {
				// JOptionPane.showMessageDialog(null,"Adding Cancelled!");
			}

		}
	}

	public class removeActor implements ActionListener {// done

		public void actionPerformed(ActionEvent e) {

			Statement st = null;
			int actorID = -1;
			try {
				frame.remove(resultPane);
				resultPane = TableCreator.createPanelWithResultSet(c, "SELECT * FROM ACTOR");
				frame.add(resultPane);
				frame.validate();
				frame.validate();

				JTextField ID = new JTextField();
				Object[] message2 = { "ID you want to remove:", ID, };
				int deleteID = -1;
				int option2 = JOptionPane.showConfirmDialog(null, message2, "Delete Actor",
						JOptionPane.OK_CANCEL_OPTION);
				if (option2 == JOptionPane.OK_OPTION) {
					if (ID.getText().equals("")) {

						JOptionPane.showMessageDialog(null, "ID Cannot be null!");
					}

					else {
						deleteID = Integer.parseInt(ID.getText());
						try {
							st = c.createStatement();
							st.executeQuery("DELETE FROM ACTOR WHERE ACTOR_ID = " + (deleteID));
							frame.remove(resultPane);
							resultPane = TableCreator.createPanelWithResultSet(c, "SELECT * FROM ACTOR");
							frame.add(resultPane);
							frame.validate();
							frame.validate();

						} catch (SQLIntegrityConstraintViolationException e2) {
							// TODO: handle exception
							st.executeQuery("DELETE FROM MOVIE_CAST WHERE ACTOR_ID = " + (deleteID));
							st.executeQuery("DELETE FROM ACTOR_AWARDS WHERE ACTOR_ID = " + (deleteID));
							st.executeQuery("DELETE FROM ACTOR WHERE ACTOR_ID = " + (deleteID));
							frame.remove(resultPane);
							resultPane = TableCreator.createPanelWithResultSet(c, "SELECT * FROM ACTOR");
							frame.add(resultPane);
							frame.validate();
						}
					}
				} else {

					// JOptionPane.showMessageDialog(null,"Adding Cancelled!");
				}

			} catch (Exception e2) {
				// TODO: handle exception
			}

		}
	}

	public class updateActor implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Statement st = null;
			int actorID = -1;
			try {
				frame.remove(resultPane);
				resultPane = TableCreator.createPanelWithResultSet(c, "SELECT * FROM ACTOR");
				frame.add(resultPane);
				frame.validate();
				frame.validate();
				JTextField ID = new JTextField();
				Object[] message2 = { "ID you want to update:", ID, };
				int updateID = -1;
				int option2 = JOptionPane.showConfirmDialog(null, message2, "Update Actor",
						JOptionPane.OK_CANCEL_OPTION);
				if (option2 == JOptionPane.OK_OPTION) {
					if (ID.getText().equals("")) {

						JOptionPane.showMessageDialog(null, "ID Cannot be null!");
					}

					else {
						updateID = Integer.parseInt(ID.getText());
						try {
							st = c.createStatement();
							JTextField name = new JTextField();
							JTextField height = new JTextField();
							JTextField weight = new JTextField();
							JTextField hair = new JTextField();
							Object[] message3 = { "New Actor Name:", name, "New Actor Height:", height,
									"New Actor Weight:", weight, "New Actor Hair:", hair };
							int option3 = JOptionPane.showConfirmDialog(null, message3, "Update Actor",
									JOptionPane.OK_CANCEL_OPTION);
							if (option3 == JOptionPane.OK_OPTION) {
								if (name.getText().equals("")) {

									JOptionPane.showMessageDialog(null, "New name cannot be empty!");
								} else {
									st.executeQuery("UPDATE ACTOR SET NAME = '" + name.getText() + "' WHERE ACTOR_ID = "
											+ (updateID));
									st.executeQuery("UPDATE ACTOR SET HEIGHT = '" + Integer.parseInt(height.getText())
											+ "' WHERE ACTOR_ID = " + (updateID));
									st.executeQuery("UPDATE ACTOR SET WEIGHT = '" + Integer.parseInt(weight.getText())
											+ "' WHERE ACTOR_ID = " + (updateID));
									st.executeQuery("UPDATE ACTOR SET HAIR = '" + hair.getText() + "' WHERE ACTOR_ID = "
											+ (updateID));
								}
							}
							frame.remove(resultPane);
							resultPane = TableCreator.createPanelWithResultSet(c, "SELECT * FROM ACTOR");
							frame.add(resultPane);
							frame.validate();
							frame.validate();
						} catch (Exception e2) {
							// TODO: handle exception
						}
					}
				} else {

					// JOptionPane.showMessageDialog(null,"Adding Cancelled!");
				}

			} catch (Exception e2) {
				// TODO: handle exception
			}
		}

	}

	public class addAward implements ActionListener {// done

		public void actionPerformed(ActionEvent e) {// done2

			JTextField awardName = new JTextField();
			JTextField awardDate = new JTextField();

			Object[] message = { "Award Name:", awardName, "Award Date (YYYY/MM/DD):", awardDate

			};
			Statement st = null;
			int awardID = -1;
			try {
				st = c.createStatement();
				ResultSet rs = st.executeQuery("SELECT MAX(AWARD_ID) FROM AWARDS");
				rs.next();
				awardID = rs.getInt(1);
				System.out.println(awardID);
			} catch (Exception e2) {
				// TODO: handle exception
			}
			int option = JOptionPane.showConfirmDialog(null, message, "New Award", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				if (awardName.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Award name cannot be empty!");
				} else {
					try {
						st.executeQuery("INSERT INTO AWARDS VALUES('" + awardName.getText() + "',TO_DATE('"
								+ awardDate.getText() + "', 'YYYY/MM/DD'),aw_id_creator.NEXTVAL)");

						JOptionPane.showMessageDialog(null,
								awardName.getText() + " successfully added to the database!");

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Something wrong has happened! Check your input!");
						e1.printStackTrace();
					}
				}

			} else {
				// JOptionPane.showMessageDialog(null,"Adding Cancelled!");
			}

		}

	}

	public class removeAward implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			Statement st = null;
			int AWARDID = -1;
			try {
				frame.remove(resultPane);
				resultPane = TableCreator.createPanelWithResultSet(c, "SELECT * FROM AWARDS");
				frame.add(resultPane);
				frame.validate();
				frame.validate();

				JTextField ID = new JTextField();
				Object[] message2 = { "ID you want to remove:", ID, };
				int deleteID = -1;
				int option2 = JOptionPane.showConfirmDialog(null, message2, "Delete AWARD",
						JOptionPane.OK_CANCEL_OPTION);
				if (option2 == JOptionPane.OK_OPTION) {
					if (ID.getText().equals("")) {

						JOptionPane.showMessageDialog(null, "ID Cannot be null!");
					}

					else {
						deleteID = Integer.parseInt(ID.getText());
						try {
							st = c.createStatement();
							st.executeQuery("DELETE FROM AWARDS WHERE AWARD_ID = " + (deleteID));
							frame.remove(resultPane);
							resultPane = TableCreator.createPanelWithResultSet(c, "SELECT * FROM AWARDS");
							frame.add(resultPane);
							frame.validate();
						} catch (SQLIntegrityConstraintViolationException e2) {
							// TODO: handle exception
							st.executeQuery("DELETE FROM ACTOR_AWARDS WHERE AWARD_ID = " + (deleteID));
							st.executeQuery("DELETE FROM MOVIE_AWARDS WHERE AWARD_ID = " + (deleteID));
							st.executeQuery("DELETE FROM DIRECTOR_AWARDS WHERE AWARD_ID = " + (deleteID));
							st.executeQuery("DELETE FROM AWARDS WHERE AWARD_ID = " + (deleteID));
							frame.remove(resultPane);
							resultPane = TableCreator.createPanelWithResultSet(c, "SELECT * FROM AWARDS");
							frame.add(resultPane);
							frame.validate();
						}
					}
				} else {

					// JOptionPane.showMessageDialog(null,"Adding Cancelled!");
				}

			} catch (Exception e2) {
				// TODO: handle exception
			}

		}

	}

	public class addStudio implements ActionListener {// done

		public void actionPerformed(ActionEvent e) {// done2

			JTextField studioName = new JTextField();
			JTextField studioCEO = new JTextField();
			JTextField studioValue = new JTextField();

			Object[] message = { "Studio Name:", studioName, "Studio Ceo: ", studioCEO, "Studio Value:", studioValue

			};
			Statement st = null;
			int studioID = -1;
			try {
				st = c.createStatement();
				ResultSet rs = st.executeQuery("SELECT MAX(STUDIO_ID) FROM STUDIO");
				rs.next();
				studioID = rs.getInt(1);
				System.out.println(studioID);
			} catch (Exception e2) {
				// TODO: handle exception
			}
			int option = JOptionPane.showConfirmDialog(null, message, "New Studio", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				if (studioName.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Studio name cannot be empty!");
				} else {
					try {
						st.executeQuery("INSERT INTO STUDIO VALUES(st_id_creator.NEXTVAL,'" + studioName.getText()
								+ "','" + studioCEO.getText() + "'," + Integer.parseInt(studioValue.getText()) + ")");

						/*
						 * table = new JFrame("ANAN");
						 * table.add(TableCreator.createPanelWithResultSet(c, "SElect * from studio"));
						 * table.setSize(new Dimension(800, 600)); table.pack(); table.setVisible(true);
						 */

						JOptionPane.showMessageDialog(null,
								studioName.getText() + " successfully added to the database!");

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Something wrong has happened! Check your input!");
						e1.printStackTrace();
					}
				}

			} else {
				// JOptionPane.showMessageDialog(null,"Adding Cancelled!");
			}
		}

	}

	public class removeStudio implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			Statement st = null;
			int studioID = -1;
			try {
				frame.remove(resultPane);
				resultPane = TableCreator.createPanelWithResultSet(c, "SELECT * FROM STUDIO");
				frame.add(resultPane);
				frame.validate();
				JTextField ID = new JTextField();
				Object[] message2 = { "ID you want to remove:", ID, };
				int deleteID = -1;
				int option2 = JOptionPane.showConfirmDialog(null, message2, "Delete studio",
						JOptionPane.OK_CANCEL_OPTION);
				if (option2 == JOptionPane.OK_OPTION) {
					if (ID.getText().equals("")) {

						JOptionPane.showMessageDialog(null, "ID Cannot be null!");
					}

					else {
						deleteID = Integer.parseInt(ID.getText());
						try {
							st = c.createStatement();
							st.executeQuery("DELETE FROM studio WHERE studio_ID = " + (deleteID));
							frame.remove(resultPane);
							resultPane = TableCreator.createPanelWithResultSet(c, "SELECT * FROM STUDIO");
							frame.add(resultPane);
							frame.validate();
						} catch (SQLIntegrityConstraintViolationException e2) {
							// TODO: handle exception
							st.executeQuery("DELETE FROM MOVIE_PROJECT WHERE STUDIO_ID = " + (deleteID));
							frame.remove(resultPane);
							resultPane = TableCreator.createPanelWithResultSet(c, "SELECT * FROM STUDIO");
							frame.add(resultPane);
							frame.validate();
						}
					}
				} else {

					// JOptionPane.showMessageDialog(null,"Adding Cancelled!");
				}

			} catch (Exception e2) {
				// TODO: handle exception
			}

		}

	}

	public class searchStudio implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			JTextField searchField = new JTextField();
			Object[] message = { "Searching Argument(s):", searchField, };
			int option = JOptionPane.showConfirmDialog(null, message, "Search Studio", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				try {
					frame.remove(resultPane);

					resultPane = TableCreator.createPanelWithResultSet(c,
							"SELECT NAME FROM STUDIO WHERE (NAME LIKE '"
									+ searchField.getText().substring(0, 1).toUpperCase()
									+ searchField.getText().substring(1) + "%') OR (NAME LIKE '%"
									+ searchField.getText() + "%') OR (NAME LIKE '%" + searchField.getText() + "')");
					frame.add(resultPane);
					frame.validate();

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Something wrong has happened! Check your input!");
					e1.printStackTrace();
				}
			}

			else {
				JOptionPane.showMessageDialog(null, "Searching Cancelled!");
			}

		}
	}

	public class searchActor implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			JTextField searchField = new JTextField();
			Object[] message = { "Searching Argument(s):", searchField, };
			int option = JOptionPane.showConfirmDialog(null, message, "Search Actor", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				try {
					frame.remove(resultPane);

					resultPane = TableCreator.createPanelWithResultSet(c,
							"SELECT * FROM ACTOR " + "WHERE (NAME LIKE '"
									+ searchField.getText().substring(0, 1).toUpperCase()
									+ searchField.getText().substring(1) + "%') OR (NAME LIKE '%"
									+ searchField.getText() + "%') OR (NAME LIKE '%" + searchField.getText() + "')");
					frame.validate();
					frame.add(resultPane);
					frame.validate();

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Something wrong has happened! Check your input!");
					e1.printStackTrace();
				}
			}

			else {
				JOptionPane.showMessageDialog(null, "Searching Cancelled!");
			}

		}
	}

	public class searchDirector implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			JTextField searchField = new JTextField();
			Object[] message = { "Searching Argument(s):", searchField, };
			int option = JOptionPane.showConfirmDialog(null, message, "Search Director", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				try {
					frame.remove(resultPane);

					resultPane = TableCreator.createPanelWithResultSet(c,
							"SELECT * FROM DIRECTOR WHERE (NAME LIKE '"
									+ searchField.getText().substring(0, 1).toUpperCase()
									+ searchField.getText().substring(0) + "%') OR (NAME LIKE '%"
									+ searchField.getText() + "%') OR (NAME LIKE '%" + searchField.getText() + "')");
					frame.validate();
					frame.add(resultPane);
					frame.validate();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Something wrong has happened! Check your input!");
					e1.printStackTrace();
				}
			}

			else {
				JOptionPane.showMessageDialog(null, "Searching Cancelled!");
			}

		}
	}

	public class searchAward implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			JTextField searchField = new JTextField();
			Object[] message = { "Searching Argument(s):", searchField, };
			int option = JOptionPane.showConfirmDialog(null, message, "Search Award", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				try {

					frame.remove(resultPane);
					resultPane = TableCreator.createPanelWithResultSet(c,
							"SELECT AWARD_NAME FROM AWARDS WHERE (AWARD_NAME LIKE '"
									+ searchField.getText().substring(0, 1).toUpperCase()
									+ searchField.getText().substring(0) + "%') OR (AWARD_NAME LIKE '%"
									+ searchField.getText() + "%') OR (AWARD_NAME LIKE '%" + searchField.getText()
									+ "')");

					frame.validate();
					frame.add(resultPane);
					frame.validate();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Something wrong has happened! Check your input!");
					e1.printStackTrace();
				}
			}

			else {
				JOptionPane.showMessageDialog(null, "Searching Cancelled!");
			}

		}
	}

	public class searchMovie implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			JTextField searchField = new JTextField();
			Object[] message = { "Searching Argument(s):", searchField, };
			int option = JOptionPane.showConfirmDialog(null, message, "Search Movie", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				try {

					frame.remove(resultPane);
					resultPane = TableCreator.createPanelWithResultSet(c,
							"SELECT * FROM MOVIE WHERE (TITLE LIKE '"
									+ searchField.getText().substring(0, 1).toUpperCase()
									+ searchField.getText().substring(0) + "%') OR (TITLE LIKE '%"
									+ searchField.getText() + "%') OR (TITLE LIKE '%" + searchField.getText() + "')");
					frame.validate();
					frame.add(resultPane);
					frame.validate();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Something wrong has happened! Check your input!");
					e1.printStackTrace();
				}
			}

			else {
				JOptionPane.showMessageDialog(null, "Searching Cancelled!");
			}

		}
	}

	public class updateAward implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Statement st = null;
			int awardID = -1;
			try {

				frame.remove(resultPane);
				resultPane = TableCreator.createPanelWithResultSet(c, "SELECT * FROM AWARDS");
				frame.add(resultPane);
				frame.validate();
				frame.validate();
				JTextField ID = new JTextField();
				Object[] message2 = { "ID you want to update:", ID, };
				int updateID = -1;
				int option2 = JOptionPane.showConfirmDialog(null, message2, "Update Award",
						JOptionPane.OK_CANCEL_OPTION);
				if (option2 == JOptionPane.OK_OPTION) {
					if (ID.getText().equals("")) {

						JOptionPane.showMessageDialog(null, "ID Cannot be null!");
					}

					else {
						updateID = Integer.parseInt(ID.getText());
						try {
							st = c.createStatement();
							JTextField movieName = new JTextField();

							JTextField movieDate = new JTextField();

							Object[] message = { "Award Name:", movieName,

									"Release Date (YYYY/MM/DD):", movieDate,

							};
							int option3 = JOptionPane.showConfirmDialog(null, message, "Update Award",
									JOptionPane.OK_CANCEL_OPTION);
							if (option3 == JOptionPane.OK_OPTION) {
								if (movieName.getText().equals("")) {

									JOptionPane.showMessageDialog(null, "New name cannot be empty!");
								} else {
									st.executeQuery("UPDATE AWARDS SET AWARD_NAME = '" + movieName.getText()
											+ "' WHERE AWARD_ID = " + (updateID));

									st.executeQuery("UPDATE AWARDS SET AWARD_DATE = TO_DATE('" + movieDate.getText()
											+ "','YYYY/MM/DD') WHERE AWARD_ID = " + (updateID));

								}
							}
							frame.remove(resultPane);
							resultPane = TableCreator.createPanelWithResultSet(c, "SELECT * FROM AWARDS");
							frame.validate();
							frame.add(resultPane);
							frame.validate();

						} catch (Exception e2) {
							// TODO: handle exception
						}
					}
				} else {

					// JOptionPane.showMessageDialog(null,"Adding Cancelled!");
				}

			} catch (Exception e2) {
				// TODO: handle exception
			}

		}

	}

	public class updateStudio implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Statement st = null;
			int awardID = -1;
			try {
				frame.remove(resultPane);
				resultPane = TableCreator.createPanelWithResultSet(c, "SELECT * FROM STUDIO");
				frame.add(resultPane);
				frame.validate();
				frame.validate();
				JTextField ID = new JTextField();
				Object[] message2 = { "ID you want to update:", ID, };
				int updateID = -1;
				int option2 = JOptionPane.showConfirmDialog(null, message2, "Update Studio",
						JOptionPane.OK_CANCEL_OPTION);
				if (option2 == JOptionPane.OK_OPTION) {
					if (ID.getText().equals("")) {

						JOptionPane.showMessageDialog(null, "ID Cannot be null!");
					}

					else {
						updateID = Integer.parseInt(ID.getText());
						try {
							st = c.createStatement();
							JTextField studioName = new JTextField();
							JTextField studioCEO = new JTextField();
							JTextField studioValue = new JTextField();

							Object[] message = { "Studio Name:", studioName, "Studio CEO:", studioCEO, "Studio Value:",
									studioValue

							};
							int option3 = JOptionPane.showConfirmDialog(null, message, "Update Studio",
									JOptionPane.OK_CANCEL_OPTION);
							if (option3 == JOptionPane.OK_OPTION) {
								if (studioName.getText().equals("")) {

									JOptionPane.showMessageDialog(null, "New name cannot be empty!");
								} else {
									st.executeQuery("UPDATE STUDIO SET NAME = '" + studioName.getText()
											+ "' WHERE STUDIO_ID = " + (updateID));
									st.executeQuery("UPDATE STUDIO SET CURRENT_CEO = '" + studioCEO.getText()
											+ "' WHERE STUDIO_ID = " + (updateID));
									st.executeQuery("UPDATE STUDIO SET STOCKMARKETVALUE = '"
											+ Integer.parseInt(studioValue.getText()) + "' WHERE STUDIO_ID = "
											+ (updateID));

								}
							}

							frame.remove(resultPane);
							resultPane = TableCreator.createPanelWithResultSet(c, "SELECT * FROM STUDIO");
							frame.validate();
							frame.add(resultPane);
							frame.validate();

						} catch (Exception e2) {
							// TODO: handle exception
						}
					}
				} else {

					// JOptionPane.showMessageDialog(null,"Adding Cancelled!");
				}

			} catch (Exception e2) {
				// TODO: handle exception
			}

		}
	}

	public class createProject implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			JTextField actors = new JTextField();
			JTextField director = new JTextField();
			JTextField studio = new JTextField();
			JTextField moovie = new JTextField();

			frame.setLayout(new GridLayout(4, 0));
			JScrollPane act = TableCreator.createPanelWithResultSet(c, "SELECT ACTOR_ID , NAME FROM ACTOR");
			JScrollPane mov = TableCreator.createPanelWithResultSet(c, "SELECT MOVIE_ID , TITLE FROM MOVIE");
			JScrollPane dir = TableCreator.createPanelWithResultSet(c, "SELECT DIRECTOR_ID , NAME FROM DIRECTOR");
			JScrollPane stu = TableCreator.createPanelWithResultSet(c, "SELECT STUDIO_ID , NAME FROM STUDIO");

			frame.add(act);
			frame.add(mov);
			frame.add(dir);
			frame.add(stu);
			frame.validate();

			Object[] message = { "Cast IDs List (seperate by comma):", actors, "Director Id:", director,
					"Producer Studio ID:", studio, "Movie ID:", moovie };

			int option = JOptionPane.showConfirmDialog(null, message, "New Movie", JOptionPane.OK_CANCEL_OPTION);

			if (option == JOptionPane.OK_OPTION) {
				String sqlS = "INSERT INTO MOVIE_PROJECT VALUES (" + Integer.parseInt(moovie.getText()) + ","
						+ Integer.parseInt(director.getText()) + "," + Integer.parseInt(studio.getText())
						+ ",mp_id_creator.nextval)";
				try {
					Statement st = c.createStatement();
					st.executeQuery(sqlS);
					if (actors.getText().contains(",")) {
						String actL[] = actors.getText().split(",");
						for (String s : actL) {
							sqlS = "INSERT INTO MOVIE_CAST VALUES (" + Integer.parseInt(moovie.getText()) + ","
									+ Integer.parseInt(s) + ")";
							st.executeQuery(sqlS);
						}
					}
					frame.removeAll();
					frame.setLayout(new GridLayout(1, 1));
					resultPane = TableCreator.createPanelWithResultSet(c,
							"SELECT ACTOR.ACTOR_ID , ACTOR.NAME , MOVIE.TITLE , MOVIE.MOVIE_ID FROM MOVIE_CAST , ACTOR , MOVIE WHERE MOVIE_CAST.MOVIE_ID = MOVIE.MOVIE_ID AND MOVIE_CAST.ACTOR_ID = ACTOR.ACTOR_ID AND MOVIE_CAST.MOVIE_ID = "
									+ moovie.getText());
					frame.add(resultPane);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		}

	}

	public class awardToActor implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}

	}

	public class awardToDirector implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

		}

	}

}
