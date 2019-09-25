package io.github.newhoo.setting;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nls.Capitalization;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * SettingConfigurable
 *
 * @author huzunrong
 * @since 1.0
 */
public class SettingConfigurable implements Configurable {

    private final PluginGlobalSetting globalSetting = PluginGlobalSetting.getInstance();
    private final PluginProjectSetting projectSetting;
    private final SettingForm settingForm;

    public SettingConfigurable(Project project) {
        this.projectSetting = new PluginProjectSetting(project);
        this.settingForm = new SettingForm(project);
    }

    @Nls(capitalization = Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Quick Dev";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        reset();

        return settingForm.mainPanel;
    }

    @Override
    public boolean isModified() {
        return projectSetting.getEnableQuickInvoke() != settingForm.invokeEnableCheckBox.isSelected()
                || projectSetting.getEnableMySQLExplain() != settingForm.mysqlExplainEnableCheckbox.isSelected()
                || projectSetting.getMysqlShowSql() != settingForm.mysqlShowSqlCheckBox.isSelected()
                || !StringUtils.equals(projectSetting.getMysqlFilter(), settingForm.mysqlFilterText.getText())
                || !StringUtils.equals(projectSetting.getMysqlTypes(), settingForm.mysqlTypesText.getText())
                || !StringUtils.equals(projectSetting.getMysqlExtras(), settingForm.mysqlExtrasText.getText())
                || !StringUtils.equals(projectSetting.getJvmParameterList(), settingForm.getJvmParameterTableText())
                || !StringUtils.equals(globalSetting.getApolloBaseUrl(), settingForm.apolloBaseUrlText.getText());
    }

    @Override
    public void apply() {
        projectSetting.setEnableQuickInvoke(settingForm.invokeEnableCheckBox.isSelected());

        projectSetting.setEnableMySQLExplain(settingForm.mysqlExplainEnableCheckbox.isSelected());
        projectSetting.setMysqlShowSql(settingForm.mysqlShowSqlCheckBox.isSelected());
        projectSetting.setMysqlFilter(settingForm.mysqlFilterText.getText());
        projectSetting.setMysqlTypes(settingForm.mysqlTypesText.getText());
        projectSetting.setMysqlExtras(settingForm.mysqlExtrasText.getText());

        projectSetting.setJvmParameter(settingForm.jvmParameterText.getText());
        projectSetting.setJvmParameterList(settingForm.getJvmParameterTableText());

        globalSetting.setApolloBaseUrl(settingForm.apolloBaseUrlText.getText());
    }

    @Override
    public void reset() {
        settingForm.invokeEnableCheckBox.setSelected(projectSetting.getEnableQuickInvoke());

        settingForm.mysqlExplainEnableCheckbox.setSelected(projectSetting.getEnableMySQLExplain());
        settingForm.mysqlShowSqlCheckBox.setSelected(projectSetting.getMysqlShowSql());
        settingForm.mysqlFilterText.setText(projectSetting.getMysqlFilter());
        settingForm.mysqlTypesText.setText(projectSetting.getMysqlTypes());
        settingForm.mysqlExtrasText.setText(projectSetting.getMysqlExtras());

        settingForm.setJvmParameterTableText(projectSetting.getJvmParameterList());

        settingForm.apolloBaseUrlText.setText(globalSetting.getApolloBaseUrl());
    }
}