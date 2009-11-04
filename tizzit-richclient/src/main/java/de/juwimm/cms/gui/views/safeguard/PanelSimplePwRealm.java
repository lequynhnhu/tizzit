/**
 * Copyright (c) 2009 Juwi MacMillan Group GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.juwimm.cms.gui.views.safeguard;

import static de.juwimm.cms.client.beans.Application.getBean;
import static de.juwimm.cms.common.Constants.rb;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import de.juwimm.cms.client.beans.Beans;
import de.juwimm.cms.gui.admin.safeguard.PanChooseLoginPage;
import de.juwimm.cms.safeguard.vo.RealmSimplePwValue;
import de.juwimm.cms.util.Communication;
import de.juwimm.cms.util.UIConstants;
import de.juwimm.swing.DropDownHolder;

/**
 * 
 * @author <a href="mailto:carsten.schalm@juwimm.com">Carsten Schalm</a>
 * company Juwi|MacMillan Group Gmbh, Walsrode, Germany
 * @version $Id$
 */
public class PanelSimplePwRealm extends JPanel implements ConfigurationInterface {
	private Communication comm = ((Communication) getBean(Beans.COMMUNICATION));
	private static final long serialVersionUID = -6113726552044888649L;
	private JComboBox jComboBoxRealms = null;
	private JButton btnAddSimpleRealm = null;
	private JButton btnManageRealm = null;
	private JButton btnDeleteRealm = null;
	private JLabel lblChooseRealm = null;
	private PanChooseLoginPage panChooseLoginPage = null;
	private PanelSelectAuthorizationRole4Realm panRequiredRole = null;

	public void fillRealmList() {
		RealmSimplePwValue[] vals = comm.getSimplePwRealmsForSite(Integer.valueOf(comm.getSiteId()));
		DefaultComboBoxModel listmodel = new DefaultComboBoxModel();
		if (vals != null) {
			for (int i = 0; i < vals.length; i++) {
				//listmodel.addElement(new DropDownHolder(vals[i], vals[i].getRealmName() + " (" + vals[i].getOwner() + ")"));
				listmodel.addElement(new DropDownHolder(vals[i], vals[i].getRealmName()));
			}
			this.panChooseLoginPage.setEnabled(true);
			this.panRequiredRole.setEnabled(true);
		} else {
			this.panChooseLoginPage.setEnabled(false);
			this.panRequiredRole.setEnabled(false);
		}
		this.jComboBoxRealms.setModel(listmodel);
	}

	public Integer getSelectedRealm() throws Exception {
		RealmSimplePwValue val = (RealmSimplePwValue) ((DropDownHolder) jComboBoxRealms.getSelectedItem()).getObject();
		return Integer.valueOf(val.getSimplePwRealmId());
	}

	public void setExistingRealm(Integer realmId) {
		for (int i = 0; i < jComboBoxRealms.getItemCount(); i++) {
			DropDownHolder val = (DropDownHolder) jComboBoxRealms.getItemAt(i);
			if (((RealmSimplePwValue) val.getObject()).getSimplePwRealmId() == realmId.intValue()) {
				jComboBoxRealms.setSelectedItem(val);
				//String owner = ((RealmSimplePwValue) val.getObject()).getOwner();
				//boolean mayEdit = (owner != null && owner.equalsIgnoreCase(comm.getUser().getUserName()) || comm.getUser().isMasterRoot());
				boolean mayEdit = true;
				this.getBtnManageRealm().setEnabled(mayEdit);
				this.getBtnDeleteRealm().setEnabled(mayEdit);
				break;
			}
		}
	}

	/**
	 * This is the default constructor
	 */
	public PanelSimplePwRealm() {
		super();
		initialize();
		this.setBorder(new TitledBorder(rb.getString("panel.panelSafeguard.realm.simple")));
	}

	/**
	 * This method initializes this
	 */
	private void initialize() {
		this.panChooseLoginPage = new PanChooseLoginPage();
		this.panChooseLoginPage.setEnabled(false);
		this.panRequiredRole = new PanelSelectAuthorizationRole4Realm();
		this.panRequiredRole.setEnabled(false);
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
		gridBagConstraints.insets = new java.awt.Insets(15, 10, 0, 0);
		gridBagConstraints.fill = java.awt.GridBagConstraints.NONE;
		gridBagConstraints.gridy = 0;
		lblChooseRealm = new JLabel();
		lblChooseRealm.setText(rb.getString("panel.panelSafeguard.realm.choose"));
		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.gridx = 3;
		gridBagConstraints11.insets = new java.awt.Insets(10, 10, 0, 10);
		gridBagConstraints11.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints11.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints11.weightx = 1.0;
		gridBagConstraints11.weighty = 1.0;
		gridBagConstraints11.gridy = 1;
		GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
		gridBagConstraints4.gridx = 3;
		gridBagConstraints4.insets = new java.awt.Insets(10, 10, 0, 10);
		gridBagConstraints4.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints4.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints4.weightx = 1.0;
		gridBagConstraints4.weighty = 1.0;
		gridBagConstraints4.gridy = 0;
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints2.gridy = 0;
		gridBagConstraints2.weightx = 1.0;
		gridBagConstraints2.insets = new java.awt.Insets(10, 10, 0, 0);
		gridBagConstraints2.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints2.gridwidth = 2;
		gridBagConstraints2.weighty = 1.0;
		gridBagConstraints2.gridx = 1;
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 1;
		gridBagConstraints1.gridheight = 1;
		gridBagConstraints1.insets = new java.awt.Insets(10, 10, 10, 0);
		gridBagConstraints1.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints1.weightx = 1.0;
		gridBagConstraints1.weighty = 1.0;
		gridBagConstraints1.gridwidth = 2;
		gridBagConstraints1.gridy = 1;
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints3.gridy = 3;
		gridBagConstraints3.weightx = 0.0;
		gridBagConstraints3.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints3.insets = new java.awt.Insets(15, 10, 10, 0);
		gridBagConstraints3.gridx = 0;
		gridBagConstraints3.gridwidth = 4;
		GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
		gridBagConstraints5.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints5.gridy = 2;
		gridBagConstraints5.weightx = 0.0;
		gridBagConstraints5.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints5.insets = new java.awt.Insets(0, 0, 0, 0);
		gridBagConstraints5.gridx = 0;
		gridBagConstraints5.gridwidth = 4;
		this.setLayout(new GridBagLayout());
		this.add(lblChooseRealm, gridBagConstraints);
		this.add(getJComboBoxRealms(), gridBagConstraints2);
		this.add(getBtnManageRealm(), gridBagConstraints4);
		this.add(getBtnDeleteRealm(), gridBagConstraints11);
		this.add(getBtnAddSimpleRealm(), gridBagConstraints1);
		this.add(panChooseLoginPage, gridBagConstraints3);
		this.add(this.panRequiredRole, gridBagConstraints5);
		this.setSize(new java.awt.Dimension(440, 223));
	}

	/**
	 * This method initializes btnAddSimpleRealm	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnAddSimpleRealm() {
		if (btnAddSimpleRealm == null) {
			btnAddSimpleRealm = new JButton();
			btnAddSimpleRealm.setText(rb.getString("panel.panelSafeguard.realm.createnew"));
			btnAddSimpleRealm.setPreferredSize(new java.awt.Dimension(120, 23));
			btnAddSimpleRealm.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CreateNewSimplePwRealmDlg dlg = new CreateNewSimplePwRealmDlg();
					dlg.setModal(true);
					dlg.setLocationRelativeTo(UIConstants.getMainFrame());
					dlg.setVisible(true);
					if (dlg.addRealm()) {
						String name = dlg.getRealmName();
						String loginpage = dlg.getLoginpage();
						int siteid = comm.getSiteId();
						int pk = comm.addSimpleRealmToSite(name, siteid, loginpage);
						DefaultComboBoxModel model = (DefaultComboBoxModel) jComboBoxRealms.getModel();
						RealmSimplePwValue val = new RealmSimplePwValue(name, pk, loginpage);
						//model.addElement(new DropDownHolder(val, val.getRealmName() + " (" + val.getOwner() + ")"));
						if (pk != -1) {
							model.addElement(new DropDownHolder(val, val.getRealmName()));
							jComboBoxRealms.updateUI();
							JOptionPane.showMessageDialog(UIConstants.getMainFrame(), rb.getString("panel.panelSafeguard.realm.created"));
						} else {
							JOptionPane.showMessageDialog(UIConstants.getMainFrame(), rb.getString("panel.panelSafeguard.realm.createFailed"));
						}
					}

				}
			});
		}
		return btnAddSimpleRealm;
	}

	/**
	 * This method initializes jComboBoxRealms	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBoxRealms() {
		if (jComboBoxRealms == null) {
			jComboBoxRealms = new JComboBox();
			jComboBoxRealms.setMaximumSize(new java.awt.Dimension(200, 20));
			jComboBoxRealms.setPreferredSize(new java.awt.Dimension(100, 23));
			jComboBoxRealms.setEditable(false);
			jComboBoxRealms.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						DropDownHolder val = (DropDownHolder) jComboBoxRealms.getSelectedItem();
						//String owner = ((RealmSimplePwValue) val.getObject()).getOwner();
						//boolean mayEdit = (owner != null && owner.equalsIgnoreCase(comm.getUser().getUserName()) || comm.getUser().isMasterRoot());
						boolean mayEdit = true;
						getBtnManageRealm().setEnabled(mayEdit);
						getBtnDeleteRealm().setEnabled(mayEdit);
					}
				}
			});
		}
		return jComboBoxRealms;
	}

	/**
	 * This method initializes btnManageRealm	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnManageRealm() {
		if (btnManageRealm == null) {
			btnManageRealm = new JButton();
			btnManageRealm.setText(rb.getString("panel.panelSafeguard.realm.manage"));
			btnManageRealm.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DropDownHolder currentElement = (DropDownHolder) jComboBoxRealms.getSelectedItem();
					if (currentElement != null) {
						RealmSimplePwValue val = (RealmSimplePwValue) currentElement.getObject();
						EditSimplePwRealmDlg dlg = new EditSimplePwRealmDlg(val);
						dlg.setTitle(rb.getString("panel.toolContent.tab.safeguard") + ": " + val.getRealmName());
						dlg.setModal(true);
						dlg.setLocationRelativeTo(UIConstants.getMainFrame());
						dlg.setVisible(true);
						String lgpage = dlg.getLoginPage();
						if (lgpage != null && !lgpage.equals(val.getLoginPageId())) {
							val.setLoginPageId(lgpage);
							comm.editSimplePwRealm(val);
						}
					}
				}
			});
		}
		return btnManageRealm;
	}

	/**
	 * This method initializes btnDeleteRealm	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnDeleteRealm() {
		if (btnDeleteRealm == null) {
			btnDeleteRealm = new JButton();
			btnDeleteRealm.setText(rb.getString("panel.panelSafeguard.btn.deleterealm"));
			btnDeleteRealm.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DropDownHolder currentElement = (DropDownHolder) jComboBoxRealms.getSelectedItem();
					if (currentElement != null) {
						RealmSimplePwValue val = (RealmSimplePwValue) currentElement.getObject();
						String name = val.getRealmName();
						int ret = JOptionPane.showConfirmDialog(UIConstants.getMainFrame(), rb.getString("panel.panelSafeguard.realm.reallydelete") + " " + name, rb.getString("panel.panelSafeguard.realm.reallydelete"), JOptionPane.YES_NO_OPTION);
						if (ret == JOptionPane.YES_OPTION) {
							val.getSimplePwRealmId();
							DefaultComboBoxModel model = (DefaultComboBoxModel) jComboBoxRealms.getModel();
							model.removeElement(currentElement);
							comm.deleteSimplePwRealm(val.getSimplePwRealmId());
						}
					}
				}
			});
		}
		return btnDeleteRealm;
	}

	public String getLoginPageViewComponentId() {
		return this.panChooseLoginPage.getLoginPageViewComponentId();
	}

	public void setLoginPageViewComponentId(String loginPageId) {
		this.panChooseLoginPage.setLoginpage(loginPageId);
	}

	public String getRequiredRole() {
		return this.panRequiredRole.getRequiredRole();
	}

	public void setRequiredRole(String requiredRole) {
		this.panRequiredRole.setRequiredRole(requiredRole);
	}

} //  @jve:decl-index=0:visual-constraint="10,10"
