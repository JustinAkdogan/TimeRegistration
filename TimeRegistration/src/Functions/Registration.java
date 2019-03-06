package Functions;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Registration {
	public String [] registrationMessage() {
		
		JTextField forname = new JTextField();
		JTextField lastname = new JTextField();
		JTextField authorizationKey = new JTextField();
		
		
		Object[] inputMessage = {
				"Vorname:", forname,
				"Nachname:", lastname,
				"Berechtigungschlüssel:", authorizationKey
		};
		
		int eingabe = JOptionPane.showConfirmDialog(null,inputMessage,"Registrierung",JOptionPane.OK_CANCEL_OPTION);
		
		String [] memberData= new String [3];
		
		if (!(forname.getText() == null) && !(lastname.getText() == null)) {
			memberData[0] = forname.getText();
			memberData[1] = lastname.getText();
			if (authorizationKey.getText().contains("admin123")) {
				memberData[2] = "2";
			}else if (authorizationKey.getText().contains("verwaltung123")) {
				memberData[2] = "1";
			}else {
				memberData[2] = "0";
			}
		}else {
			registrationMessage();
		}
		
		return memberData;
	}
}
