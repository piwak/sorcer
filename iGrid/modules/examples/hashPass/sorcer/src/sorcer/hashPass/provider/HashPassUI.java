package sorcer.hashPass.provider;



import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.jini.core.lookup.ServiceItem;
import net.jini.lookup.entry.UIDescriptor;
import net.jini.lookup.ui.MainUI;
import sorcer.hashPass.provider.HashPass;
import sorcer.ui.serviceui.UIComponentFactory;
import sorcer.ui.serviceui.UIDescriptorFactory;
import sorcer.util.Sorcer;


public class HashPassUI extends JPanel {

	private static final long serialVersionUID = -3171243785170712405L;

	private JTextField passTextField;
	private JLabel hashTextField;
	private ServiceItem item;
	private HashPass hashPass;
	
	private final static Logger logger = Logger.getLogger(HashPassUI.class
			.getName());

	public HashPassUI(Object provider) {
		super();
		getAccessibleContext().setAccessibleName("HashPass");
		item = (ServiceItem) provider;

		if (item.service instanceof HashPass) {
			hashPass = (HashPass) item.service;
			createUI();
			setFields();
		}
	}
	
	protected void createUI() {
		setLayout(new BorderLayout());
		add(buildPanel(), BorderLayout.CENTER);
	}
	
	private void setFields(){
		try{
			HashPassEntity hashPassEntity=hashPass.initializeInputFields();
			String bd=hashPassEntity.getPass();
			passTextField.setText(bd);
			String bd2=hashPassEntity.getHash();
			hashTextField.setText(bd2);	
		}catch(Exception e){
			
		}
	}
	
	private HashPassEntity getPassFromPassTextField(JTextField textField){
		String password=textField.getText();
		HashPassEntity passEntity=new HashPassEntity();
		passEntity.setPass(password);
		return passEntity;
	}
	
	private class hashPasswordAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				HashPassEntity passToHash = getPassFromPassTextField(passTextField);
				hashPass.hashPassword(passToHash);
				hashTextField.setText(hashPass.initializeInputFields().getHash());
				
			} catch (Exception exception) {
				logger.info("Couldn't talk to account. Error was" + exception);
				logger.throwing(getClass().getName(), "actionPerformed",
						exception);
			}
		}
	}

	private JPanel buildPanel() {
		
		JPanel panel = new JPanel();
		JPanel actionPanel = new JPanel(new GridLayout(3, 2, 5, 5));

		actionPanel.add(new JLabel("password:"));
		passTextField = new JTextField();
		passTextField.setEnabled(true);
		actionPanel.add(passTextField);
		
		actionPanel.add(new JLabel("hash:"));
		hashTextField = new JLabel("");
		hashTextField.setEnabled(false);
		actionPanel.add(hashTextField);
		actionPanel.add(new JLabel(""));
		JButton hashButton = new JButton("Hash password!");
		hashButton.addActionListener(new hashPasswordAction());
		actionPanel.add(hashButton);
		panel.add(actionPanel);

		
		return panel;
		
	}
	
	public static UIDescriptor getUIDescriptor() {
		UIDescriptor uiDesc = null;
		try {
			uiDesc = UIDescriptorFactory.getUIDescriptor(MainUI.ROLE,
					new UIComponentFactory(new URL[] { new URL(Sorcer
							.getWebsterUrl()
							+ "/hashPass.jar") }, HashPassUI.class.getName()));
		} catch (Exception ex) {
			logger.throwing(HashPassUI.class.getName(), "getUIDescriptor", ex);
		}
		return uiDesc;
	}

}

