package view;

import java.util.EventListener;

public interface LogoutButtonListener extends EventListener {
	public void logoutButtonClicked(LogoutButtonEventObject ev);
}