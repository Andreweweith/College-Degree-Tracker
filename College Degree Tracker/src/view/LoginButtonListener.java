package view;

import java.util.EventListener;

public interface LoginButtonListener extends EventListener {
	public void loginButtonClicked(LoginButtonEventObject ev);
}
