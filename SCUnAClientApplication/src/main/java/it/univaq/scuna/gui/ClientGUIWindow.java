package it.univaq.scuna.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import it.univaq.scuna.client.ClientApplicationLogic;
import it.univaq.scuna.common.to.UserDataTO;

public class ClientGUIWindow extends JFrame{

	private static final long serialVersionUID = 8598374511489039927L;

	private static final String CARD_ID = "Card ID";
	private static final String USER_GROUP_ID = "User Group ID";
	private static final String USER_NAME = "User Name";
	private static final String AREA_CODE = "Area Code";
	private static final String PEOPLE_NUMBER = "People number";
	private static final String EXAM_ID = "Exam ID";
	private static final String LESSON_ID = "Lesson ID";

	private final JTextField areaField;
	private final JTextField cardIdField;
	private final JTextField groupField;

	private final JTextField peopleNumberField;
	private final JTextField examField;
	private final JTextField lessonField;

	private final JTextArea areaDisplay;
	private final JTextArea cardDisplay;
	private final JTextArea groupDisplay;
	private final JTextArea nameDisplay;

	private final JButton sendCrowdingDataButton;
	private final JButton registerExamButton;
	private final JButton presenceConfirmationButton;
	private final JButton acessAreaButton;
	private final JButton exitAreaButton;

	private boolean areaSelected;
	private boolean userSelected;

	private final ClientApplicationLogic clientApplication;

	public ClientGUIWindow(final ClientApplicationLogic clientApplication) {
		super();
		this.clientApplication = clientApplication;

		this.sendCrowdingDataButton = new JButton("Send Data");
		this.registerExamButton = new JButton("Register to exam");
		this.presenceConfirmationButton = new JButton("Confirm presence");
		this.acessAreaButton = new JButton("Access Area");
		this.exitAreaButton = new JButton("Exit Area");

		this.cardIdField = new JTextField(CARD_ID, 20);
		cardIdField.setForeground(Color.GRAY);;
		cardIdField.addFocusListener(new FocusListener() {
		    public void focusGained(FocusEvent e) {
		        if (cardIdField.getText().equals(CARD_ID)) {
		        	cardIdField.setText("");
		        	cardIdField.setForeground(Color.BLACK);
		        }
		    }
		    public void focusLost(FocusEvent e) {
		        if (cardIdField.getText().isEmpty()) {
		        	cardIdField.setForeground(Color.GRAY);
		        	cardIdField.setText(CARD_ID);
		        }
		    }
	    });

		this.groupField = new JTextField(USER_GROUP_ID, 20);
		groupField.setForeground(Color.GRAY);;
		groupField.addFocusListener(new FocusListener() {
		    public void focusGained(FocusEvent e) {
		        if (groupField.getText().equals(USER_GROUP_ID)) {
		        	groupField.setText("");
		        	groupField.setForeground(Color.BLACK);
		        }
		    }
		    public void focusLost(FocusEvent e) {
		        if (groupField.getText().isEmpty()) {
		        	groupField.setForeground(Color.GRAY);
		        	groupField.setText(USER_GROUP_ID);
		        }
		    }
	    });

		this.areaField = new JTextField(AREA_CODE, 20);
		areaField.setForeground(Color.GRAY);;
		areaField.addFocusListener(new FocusListener() {
		    public void focusGained(FocusEvent e) {
		        if (areaField.getText().equals(AREA_CODE)) {
		        	areaField.setText("");
		        	areaField.setForeground(Color.BLACK);
		        }
		    }
		    public void focusLost(FocusEvent e) {
		        if (areaField.getText().isEmpty()) {
		        	areaField.setForeground(Color.GRAY);
		        	areaField.setText(AREA_CODE);
		        }
		    }
	    });

		this.peopleNumberField = new JTextField(PEOPLE_NUMBER, 15);
		peopleNumberField.setForeground(Color.GRAY);;
		peopleNumberField.addFocusListener(new FocusListener() {
		    public void focusGained(FocusEvent e) {
		        if (peopleNumberField.getText().equals(PEOPLE_NUMBER)) {
		        	peopleNumberField.setText("");
		        	peopleNumberField.setForeground(Color.BLACK);
		        }
		    }
		    public void focusLost(FocusEvent e) {
		        if (peopleNumberField.getText().isEmpty()) {
		        	peopleNumberField.setForeground(Color.GRAY);
		        	peopleNumberField.setText(PEOPLE_NUMBER);
		        }
		    }
	    });

		this.examField = new JTextField(EXAM_ID, 15);
		examField.setForeground(Color.GRAY);;
		examField.addFocusListener(new FocusListener() {
		    public void focusGained(FocusEvent e) {
		        if (examField.getText().equals(EXAM_ID)) {
		        	examField.setText("");
		        	examField.setForeground(Color.BLACK);
		        }
		    }
		    public void focusLost(FocusEvent e) {
		        if (examField.getText().isEmpty()) {
		        	examField.setForeground(Color.GRAY);
		        	examField.setText(EXAM_ID);
		        }
		    }
	    });

		this.lessonField = new JTextField(LESSON_ID, 15);
		lessonField.setForeground(Color.GRAY);;
		lessonField.addFocusListener(new FocusListener() {
		    public void focusGained(FocusEvent e) {
		        if (lessonField.getText().equals(LESSON_ID)) {
		        	lessonField.setText("");
		        	lessonField.setForeground(Color.BLACK);
		        }
		    }
		    public void focusLost(FocusEvent e) {
		        if (lessonField.getText().isEmpty()) {
		        	lessonField.setForeground(Color.GRAY);
		        	lessonField.setText(LESSON_ID);
		        }
		    }
	    });

		this.areaDisplay = new JTextArea();
		areaDisplay.setEditable(false);
		areaDisplay.setOpaque(false);
		areaDisplay.setText(AREA_CODE+": N/A");
		areaDisplay.setPreferredSize(new Dimension(315, 40));

		this.cardDisplay = new JTextArea();
		cardDisplay.setEditable(false);
		cardDisplay.setOpaque(false);
		cardDisplay.setText(CARD_ID+": N/A");
		cardDisplay.setPreferredSize(new Dimension(315, 20));

		this.groupDisplay = new JTextArea();
		groupDisplay.setEditable(false);
		groupDisplay.setOpaque(false);
		groupDisplay.setText(USER_GROUP_ID+": N/A");
		groupDisplay.setPreferredSize(new Dimension(315, 20));
		
		this.nameDisplay = new JTextArea();
		nameDisplay.setEditable(false);
		nameDisplay.setOpaque(false);
		nameDisplay.setText(USER_NAME+": N/A");
		nameDisplay.setPreferredSize(new Dimension(315, 20));

		this.areaSelected = false;
		this.userSelected = false;

		this.initWindow();
	}

	private void initWindow() {
		this.setTitle("SCUnA Smart Card System");
		this.setSize(674, 335);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		Container container = getContentPane();
		container.setLayout(new BorderLayout());

		JPanel topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(0, 10));

		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(299, 0));
		leftPanel.setLayout(new BorderLayout());

		JPanel rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(374, 0));
		rightPanel.setLayout(new BorderLayout());

		JPanel bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(0, 10));

		JPanel middlePanel = new JPanel();
		middlePanel.setBackground(Color.GRAY);

		JPanel leftTop = new JPanel();
		leftTop.setPreferredSize(new Dimension(0, 120));

		JPanel leftBottom = new JPanel();
		leftBottom.setPreferredSize(new Dimension(0, 180));

		JPanel rightTop = new JPanel();
		rightTop.setPreferredSize(new Dimension(0, 122));

		JPanel rightMiddle = new JPanel();
		rightMiddle.setPreferredSize(new Dimension(0, 2));
		rightMiddle.setBackground(Color.GRAY);
	
		JPanel rightBottom = new JPanel();
		rightBottom.setPreferredSize(new Dimension(0, 170));

		JButton initAreaButton = new JButton("Simulate Bootstrap");
		initAreaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = areaField.getText();
				if (input.equals(AREA_CODE) || input.equals("")) {
					JOptionPane.showMessageDialog(null, "Insert an area code");
				} else if (input.contains(" ")) {
					JOptionPane.showMessageDialog(null, "Invalid Area ID");
				} else {
					clientApplication.setAreaCode(input);
					if (clientApplication.getRestrictedAreaAllowedGroups() == null || clientApplication.getRestrictedAreaAllowedGroups().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Invalid Area ID");
						ClientGUIWindow.this.areaDisplay.setText(AREA_CODE+": N/A");
					} else {
						updateWindowAreaData(input);
						JOptionPane.showMessageDialog(null, clientApplication.getRestrictedAreaAllowedGroups());
					}
				}
			}
		});

		JButton smartCardButton = new JButton("Simulate Card Reading");
		smartCardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cardIdInput = cardIdField.getText();
				String groupInput = groupField.getText();
				if (cardIdInput.equals(CARD_ID) || cardIdInput.equals("")) {
					JOptionPane.showMessageDialog(null, "Insert a Card ID");
				} else if (groupInput.equals(USER_GROUP_ID) || groupInput.equals("")) {
					JOptionPane.showMessageDialog(null, "Insert a group name");
				} else {
					clientApplication.setUserData(cardIdInput, groupInput);
					updateWindowUserData(cardIdInput, groupInput);
				}
			}
		});

		sendCrowdingDataButton.setPreferredSize(new Dimension(130, 20));
		sendCrowdingDataButton.setEnabled(false);
		sendCrowdingDataButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = peopleNumberField.getText();
				if (input.equals(PEOPLE_NUMBER) || input.equals("")) {
					JOptionPane.showMessageDialog(null, "Insert a number");
				} else if (!input.matches("\\d+")) {
					JOptionPane.showMessageDialog(null, "The input is not an integer number");
				} else {
					int number = Integer.valueOf(input);
					clientApplication.sendCrowdingData(number);
					peopleNumberField.setForeground(Color.GRAY);
					peopleNumberField.setText(PEOPLE_NUMBER);
				}
			}
		});

		registerExamButton.setPreferredSize(new Dimension(130, 20));
		registerExamButton.setEnabled(false);
		registerExamButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = examField.getText();
				if (input.equals(EXAM_ID) || input.equals("")) {
					JOptionPane.showMessageDialog(null, "Insert exam ID");
				} else {
					UserDataTO userDataTO = clientApplication.askExamAuthentication(input);
					examField.setForeground(Color.GRAY);
					examField.setText(EXAM_ID);
					if (userDataTO != null) {
						JOptionPane.showMessageDialog(null, "Confirm presence: "
								+userDataTO.getBadgeID()+", "
								+userDataTO.getName()+" "+userDataTO.getSurname()
								+", birthdate: "+userDataTO.getBirthdate());
						clientApplication.examConfirmation(input);
					} else {
						JOptionPane.showMessageDialog(null, "User not registered for the exam");
					}
				}
			}
		});

		presenceConfirmationButton.setPreferredSize(new Dimension(130, 20));
		presenceConfirmationButton.setEnabled(false);
		presenceConfirmationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = lessonField.getText();
				if (input.equals(LESSON_ID) || input.equals("")) {
					JOptionPane.showMessageDialog(null, "Insert lesson ID");
				} else {
					clientApplication.lessonConfirmation(input);
					lessonField.setForeground(Color.GRAY);
					lessonField.setText(LESSON_ID);
				}
			}
		});

		acessAreaButton.setPreferredSize(new Dimension(100, 20));
		acessAreaButton.setEnabled(false);
		acessAreaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(clientApplication.accessArea()) {
					JOptionPane.showMessageDialog(null, "User allowed to access this restricted area");
					clientApplication.sendAccessData();
				} else {
					JOptionPane.showMessageDialog(null, "User NOT allowed to access this restricted area");
				}
			}
		});

		exitAreaButton.setPreferredSize(new Dimension(100, 20));
		exitAreaButton.setEnabled(false);
		exitAreaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clientApplication.exitArea();
			}
		});

		JTextArea smartCardLabel = new JTextArea("Smart Card");
		smartCardLabel.setEditable(false);
		smartCardLabel.setOpaque(false);

		JTextArea areaCodeLabel = new JTextArea("Area Code");
		areaCodeLabel.setEditable(false);
		areaCodeLabel.setOpaque(false);

		JTextArea actionSpacerBefore = new JTextArea("");
		actionSpacerBefore.setPreferredSize(new Dimension(315, 20));
		actionSpacerBefore.setEditable(false);
		actionSpacerBefore.setOpaque(false);

		JTextArea actionSpacer = new JTextArea("");
		actionSpacer.setPreferredSize(new Dimension(315, 10));
		actionSpacer.setEditable(false);
		actionSpacer.setOpaque(false);

		rightTop.add(areaDisplay);
		rightTop.add(cardDisplay);
		rightTop.add(groupDisplay);
		//rightTop.add(nameDisplay);

		rightBottom.add(actionSpacerBefore);
		rightBottom.add(peopleNumberField);
		rightBottom.add(sendCrowdingDataButton);
		rightBottom.add(examField);
		rightBottom.add(registerExamButton);
		rightBottom.add(lessonField);
		rightBottom.add(presenceConfirmationButton);
		rightBottom.add(actionSpacer);
		rightBottom.add(acessAreaButton);
		rightBottom.add(exitAreaButton);

		leftTop.add(areaCodeLabel);
		leftTop.add(areaField);
		leftTop.add(initAreaButton);

		leftBottom.add(smartCardLabel);
		leftBottom.add(cardIdField);
		leftBottom.add(groupField);
		leftBottom.add(smartCardButton);

		leftPanel.add(leftTop, BorderLayout.NORTH);
		leftPanel.add(leftBottom, BorderLayout.SOUTH);

		rightPanel.add(rightTop, BorderLayout.NORTH);
		rightPanel.add(rightMiddle, BorderLayout.CENTER);
		rightPanel.add(rightBottom, BorderLayout.SOUTH);

		container.add(topPanel, BorderLayout.NORTH);
		container.add(leftPanel, BorderLayout.WEST);
		container.add(rightPanel, BorderLayout.EAST);
		container.add(bottomPanel, BorderLayout.SOUTH);
		container.add(middlePanel, BorderLayout.CENTER);
	}

	private void updateWindowAreaData(final String areaCode) {
    	areaField.setForeground(Color.GRAY);
    	areaField.setText(AREA_CODE);
    	areaDisplay.setText(AREA_CODE+": "+areaCode);

    	this.areaSelected = true;
    	sendCrowdingDataButton.setEnabled(true);
    	if (this.userSelected) {
    		acessAreaButton.setEnabled(true);
    		exitAreaButton.setEnabled(true);
    	}
	}

	private void updateWindowUserData(final String cardId, final String group) {
    	cardIdField.setForeground(Color.GRAY);
    	cardIdField.setText(CARD_ID);
    	groupField.setForeground(Color.GRAY);
    	groupField.setText(USER_GROUP_ID);

		cardDisplay.setText(CARD_ID+": "+cardId);
		groupDisplay.setText(USER_GROUP_ID+": "+group);

		this.userSelected = true;
		registerExamButton.setEnabled(true);
		presenceConfirmationButton.setEnabled(true);
		if(this.areaSelected) {
			acessAreaButton.setEnabled(true);
			exitAreaButton.setEnabled(true);
		}
	}
}
