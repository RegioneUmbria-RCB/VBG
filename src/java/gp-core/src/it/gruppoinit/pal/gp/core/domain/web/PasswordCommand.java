package it.gruppoinit.pal.gp.core.domain.web;

public class PasswordCommand extends BaseCommand {

    private String password;
    private String newPassword;
    private String newPasswordConfirm;

    public PasswordCommand() {

    }

    public String getPassword() {

	return password;
    }

    public void setPassword(String password) {

	this.password = password;
    }

    public String getNewPassword() {

	return newPassword;
    }

    public void setNewPassword(String newPassword) {

	this.newPassword = newPassword;
    }

    public String getNewPasswordConfirm() {

	return newPasswordConfirm;
    }

    public void setNewPasswordConfirm(String newPasswordConfirm) {

	this.newPasswordConfirm = newPasswordConfirm;
    }
}
