package de.juwimm.cms.gui;

import static de.juwimm.cms.client.beans.Application.getBean;
import static de.juwimm.cms.common.Constants.rb;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import org.apache.log4j.Logger;
import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.icon.ImageWrapperResizableIcon;
import org.jvnet.flamingo.common.model.ActionToggleButtonModel;

import de.juwimm.cms.Messages;
import de.juwimm.cms.client.beans.Beans;
import de.juwimm.cms.common.Constants;
import de.juwimm.cms.common.UserRights;
import de.juwimm.cms.deploy.actions.ExportFullThread;
import de.juwimm.cms.deploy.actions.ImportFullThread;
import de.juwimm.cms.gui.controls.ColapsePanel;
import de.juwimm.cms.gui.ribbon.CommandButton;
import de.juwimm.cms.util.ActionHub;
import de.juwimm.cms.util.Communication;
import de.juwimm.cms.util.UIConstants;

/**
 * @author <a href="florin.zalum@juwimm.com">Florin Zalum</a>
 * @version $Id$
 */
public class OptionsDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1042287156390559221L;
	private static Logger log = Logger.getLogger(OptionsDialog.class);

	private JLabel lblPasswd;
	private JCheckBox changePasswordCheckBox;
	private JLabel lblPasswdRepeat;
	private JPasswordField txtNewPassword1;
	private JPasswordField txtNewPassword2;
	private String whom;
	private boolean isImportExportEnabled = true;
	private JCommandButton generalOption;
	private JCommandButton importExportOption;
	private JCommandButton userOption;
	private Communication comm;

	private JButton clearCacheButton;
	private JButton reloadDcfButton;
	private JButton importButton;
	private JButton exportButton;
	private JButton changePasswordButton;

	private JPanel ribbonBandContainer;
	private JPanel optionsContainer;

	private JPanel generalPanel;
	private JPanel importExportPanel;
	private JPanel userInformationPanel;
	private ColapsePanel changePasswordPanel;

	public static final String CMS_CLEAR_CACHE = "cmsclearcachenow!";
	public static final String CMS_RELOAD_DCF = "cmsreloaddcf";

	public static final String CMS_EXPORT_ALL = "cmsexportallthefuck";
	public static final String CMS_IMPORT_ALL = "cmsimportallthisshittystuffassoonaspossibletogetitrunning";

	public OptionsDialog(final Communication comm) {
		super(UIConstants.getMainFrame(), Constants.rb.getString("ribbon.options"), true);
		this.comm = comm;
		this.whom = comm.getUser().getUserName();
		ActionHub.addActionListener(this);
		Dimension size = new Dimension(265, 240);
		this.setSize(size);
		this.setIconImage(UIConstants.CMS.getImage());
		this.setLocationRelativeTo(UIConstants.getMainFrame());
		this.setMinimumSize(size);
		this.setResizable(false);
		isImportExportEnabled = userIsSiteRoot();
		generalOption = new CommandButton(Constants.rb.getString("optionsDialog.generalOption"), ImageWrapperResizableIcon.getIcon(UIConstants.RIBBON_GENERAL_OPTIONS.getImage(), new Dimension(32, 32)));
		importExportOption = new CommandButton(Constants.rb.getString("optionsDialog.importExportOption"), ImageWrapperResizableIcon.getIcon(UIConstants.RIBBON_IMPORT_EXPORT_OPTIONS.getImage(), new Dimension(32, 32)));
		userOption = new CommandButton(Constants.rb.getString("optionsDialog.userOption"), ImageWrapperResizableIcon.getIcon(UIConstants.RIBBON_USER_OPTIONS.getImage(), new Dimension(32, 32)));
		if (!isImportExportEnabled) {
			importExportOption.setEnabled(false);
		}
		initOptionsRibbonsListeners();
		ribbonBandContainer = new JPanel();
		optionsContainer = new JPanel();
		GridBagLayout gridBagLayout = new GridBagLayout();
		ribbonBandContainer.setLayout(gridBagLayout);
		GridBagConstraints generalOptionConstraint = new GridBagConstraints();
		GridBagConstraints importExportOptionConstraint = new GridBagConstraints();
		GridBagConstraints userInformationOptionConstraint = new GridBagConstraints();
		generalOptionConstraint.insets = new Insets(4, 4, 4, 4);
		importExportOptionConstraint.insets = new Insets(4, 4, 4, 4);
		userInformationOptionConstraint.insets = new Insets(4, 4, 4, 4);
		generalOptionConstraint.anchor = GridBagConstraints.WEST;
		importExportOptionConstraint.anchor = GridBagConstraints.WEST;
		userInformationOptionConstraint.anchor = GridBagConstraints.WEST;

		importExportOptionConstraint.gridx = 1;
		userInformationOptionConstraint.gridx = 2;
		ribbonBandContainer.setBorder(BorderFactory.createLineBorder(Color.gray));
		ribbonBandContainer.add(generalOption, generalOptionConstraint);
		ribbonBandContainer.add(importExportOption, importExportOptionConstraint);
		ribbonBandContainer.add(userOption, userInformationOptionConstraint);

		getContentPane().setLayout(new BorderLayout());

		getContentPane().add(ribbonBandContainer, BorderLayout.NORTH);
		getContentPane().add(optionsContainer, BorderLayout.CENTER);

		constructGeneralOptionsPane();
		constructImportExportOptionsPane();
		constructUserInformationPane();

		optionsContainer.add(generalPanel);
		optionsContainer.add(importExportPanel);
		optionsContainer.add(userInformationPanel);
		generalPanel.setVisible(true);
		importExportPanel.setVisible(false);
		userInformationPanel.setVisible(false);
		initListeners();

	}

	private void constructGeneralOptionsPane() {
		generalPanel = new JPanel(new BorderLayout());
		clearCacheButton = new JButton(Constants.rb.getString("optionsDialog.generalOption.clearcache"));
		reloadDcfButton = new JButton(Constants.rb.getString("optionsDialog.generalOption.reloadDcf"));
		generalPanel.add(clearCacheButton, BorderLayout.CENTER);
		generalPanel.add(reloadDcfButton, BorderLayout.SOUTH);
	}

	private void constructImportExportOptionsPane() {
		importExportPanel = new JPanel(new BorderLayout());
		importButton = new JButton(Constants.rb.getString("optionsDialog.importOption"));
		exportButton = new JButton(Constants.rb.getString("optionsDialog.exportOption"));
		importExportPanel.add(importButton, BorderLayout.CENTER);
		importExportPanel.add(exportButton, BorderLayout.SOUTH);
	}

	private void constructUserInformationPane() {
		userInformationPanel = new JPanel(new GridBagLayout());
		changePasswordPanel = new ColapsePanel();
		changePasswordPanel.setLayout(new GridBagLayout());
		changePasswordPanel.setText(Messages.getString("frame.changePasswd.whomMessage", whom));
		//changePasswordPanel.setBorder(new MyBorder(BorderFactory.createEtchedBorder(Color.white, new Color(165, 163, 151))));

		lblPasswd = new JLabel();
		lblPasswdRepeat = new JLabel();
		txtNewPassword1 = new JPasswordField();
		txtNewPassword2 = new JPasswordField();
		changePasswordButton = new JButton(Messages.getString("menubar.extras.changePassword"));
		changePasswordCheckBox = new JCheckBox(Messages.getString("frame.changePasswd.whomMessage", whom));

		lblPasswdRepeat.setText(rb.getString("frame.changePasswd.newPasswd"));
		lblPasswd.setText(rb.getString("frame.changePasswd.repeatPasswd"));

		//userInformationPanel.add(changePasswordCheckBox, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		userInformationPanel.add(changePasswordPanel, new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		changePasswordPanel.add(lblPasswdRepeat, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 4, 0, 4), 0, 0));
		changePasswordPanel.add(lblPasswd, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 0, 0), 0, 0));
		changePasswordPanel.add(changePasswordButton, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
		changePasswordPanel.add(txtNewPassword1, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 0, 4), 0, 0));
		changePasswordPanel.add(txtNewPassword2, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 0, 4), 0, 0));
	}

	private void initListeners() {
		clearCacheButton.addActionListener(this);
		reloadDcfButton.addActionListener(this);
		importButton.addActionListener(this);
		exportButton.addActionListener(this);

		clearCacheButton.setActionCommand(CMS_CLEAR_CACHE);
		reloadDcfButton.setActionCommand(CMS_RELOAD_DCF);
		importButton.setActionCommand(CMS_IMPORT_ALL);
		exportButton.setActionCommand(CMS_EXPORT_ALL);

		changePasswordCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enableChangePasswordpanel(changePasswordCheckBox.isSelected());
			}

		});

		changePasswordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cmdChangeActionPerformed(e);
			}
		});
	}

	private void enableChangePasswordpanel(boolean state) {
		//		txtNewPassword1.setEnabled(state);
		//		txtNewPassword2.setEnabled(state);
		//		cmdChange.setEnabled(state);
		changePasswordPanel.setVisible(state);
	}

	/**
	 * 
	 */
	private void initOptionsRibbonsListeners() {
		generalOption.setActionModel(new ActionToggleButtonModel(false));
		if (isImportExportEnabled) {
			importExportOption.setActionModel(new ActionToggleButtonModel(false));
		}
		userOption.setActionModel(new ActionToggleButtonModel(false));
		generalOption.getActionModel().setSelected(true);
		generalOption.getActionModel().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (isImportExportEnabled) {
					importExportOption.getActionModel().setSelected(false);
				}
				generalOption.getActionModel().setSelected(true);
				userOption.getActionModel().setSelected(false);
				generalPanel.setVisible(true);
				importExportPanel.setVisible(false);
				userInformationPanel.setVisible(false);
				pack();
			}

		});
		if (isImportExportEnabled) {
			importExportOption.getActionModel().addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					importExportOption.getActionModel().setSelected(true);
					generalOption.getActionModel().setSelected(false);
					userOption.getActionModel().setSelected(false);
					generalPanel.setVisible(false);
					importExportPanel.setVisible(true);
					userInformationPanel.setVisible(false);
					pack();
				}

			});
		}
		userOption.getActionModel().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (isImportExportEnabled) {
					importExportOption.getActionModel().setSelected(false);
				}
				generalOption.getActionModel().setSelected(false);
				userOption.getActionModel().setSelected(true);
				generalPanel.setVisible(false);
				importExportPanel.setVisible(false);
				userInformationPanel.setVisible(true);
				pack();
			}

		});
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(CMS_CLEAR_CACHE)) {
			comm.clearSvgCache();
			try {
				if (comm.getDbHelper().clearMyCache()) {
					JOptionPane.showMessageDialog(UIConstants.getMainFrame(), rb.getString("panel.cmsMenubar.cacheCleared"), rb.getString("dialog.title"), JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(UIConstants.getMainFrame(), rb.getString("exception.cantClearCache"), rb.getString("dialog.title"), JOptionPane.ERROR_MESSAGE);
				}
			} catch (Exception exe) {
				if (log.isDebugEnabled()) {
					log.debug(exe.getMessage());
				}
			}
		} else if (e.getActionCommand().equals(CMS_RELOAD_DCF)) {
			PanLogin.loadTemplates(false);
			ActionHub.fireActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, Constants.ACTION_VIEW_EDITOR));
		} else if (e.getActionCommand().equals(CMS_EXPORT_ALL)) {
			new ExportFullThread().start();
			//EXPORT ALL
		} else if (e.getActionCommand().equals(CMS_IMPORT_ALL)) {
			new ImportFullThread().start();
			//IMPORT ALL
		}
	}

	private void cmdChangeActionPerformed(ActionEvent e) {
		if (String.copyValueOf(txtNewPassword1.getPassword()).equals(String.copyValueOf(txtNewPassword2.getPassword()))) {
			try {
				((Communication) getBean(Beans.COMMUNICATION)).changePassword(whom, String.copyValueOf(txtNewPassword2.getPassword()));
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, rb.getString("frame.changePasswd.msgError") + "\n" + ex.getMessage().toString(), rb.getString("dialog.title"), JOptionPane.ERROR_MESSAGE);
				return;
			}
			UIConstants.setStatusInfo(rb.getString("frame.changePasswd.msgSucc"));
			JOptionPane.showMessageDialog(this, rb.getString("frame.changePasswd.msgSucc"), rb.getString("dialog.title"), JOptionPane.INFORMATION_MESSAGE);

		} else {
			JOptionPane.showMessageDialog(this, rb.getString("frame.changePasswd.msgNoMatch"), rb.getString("dialog.title"), JOptionPane.ERROR_MESSAGE);
			txtNewPassword1.setText("");
			txtNewPassword2.setText("");
			txtNewPassword1.requestFocus();
		}
	}

	private boolean userIsSiteRoot() {
		return comm.isUserInRole(UserRights.SITE_ROOT);
	}

}
