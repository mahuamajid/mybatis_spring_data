package com.mahua.adapter;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.TableConfiguration;

import java.util.List;

public class EntityClassPlugin extends PluginAdapter {
    private FullyQualifiedJavaType typeEntity;
    private FullyQualifiedJavaType typeId;
    private FullyQualifiedJavaType typeGeneratedValue;
    private FullyQualifiedJavaType typeGenericGenerator;
    private FullyQualifiedJavaType typeRepository;

    public EntityClassPlugin() {
        super();
        typeEntity = new FullyQualifiedJavaType("javax.persistence.Entity");
        typeId = new FullyQualifiedJavaType("javax.persistence.Id");
        typeGeneratedValue = new FullyQualifiedJavaType("javax.persistence.GeneratedValue");
        typeGenericGenerator = new FullyQualifiedJavaType("org.hibernate.annotations.GenericGenerator");
        typeRepository = new FullyQualifiedJavaType("org.springframework.stereotype.Repository");
    }

    public boolean validate(List<String> warnings) {
        return true;
    }


    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        addEntityAnnotation(topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        addIdAnnotation(field, introspectedTable);
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        addRepositoryAnnotation(interfaze, introspectedTable);
        return true;
    }

    private void addEntityAnnotation(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addImportedType(typeEntity);
        topLevelClass.addImportedType(typeId);
        topLevelClass.addImportedType(typeGeneratedValue);
        topLevelClass.addImportedType(typeGenericGenerator);

        TableConfiguration tableConfiguration = introspectedTable.getTableConfiguration();

        String domainObjectName = tableConfiguration.getDomainObjectName();
        String alias = domainObjectName == null ? tableConfiguration.getTableName() : domainObjectName;
        String aliasAnnotation = String.format("@Entity", alias);

        topLevelClass.addAnnotation(aliasAnnotation);
    }

    private void addIdAnnotation(Field field, IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();

        String primaryKey = primaryKeyColumns.get(0).getJavaProperty();
        String primaryKeyType = primaryKeyColumns.get(0).getJdbcTypeName();

        String idAnnotation = String.format("@Id", primaryKey);
        String generatedValueAnnotation1 = String.format("@GeneratedValue(generator = \"uuid\")", primaryKey);
        String generatedValueAnnotation2 = String.format("@GeneratedValue(strategy = GenerationType.AUTO)", primaryKey);
        String genericGeneratorAnnotation = String.format("@GenericGenerator(name = \"uuid\", strategy = \"uuid2\")", primaryKey);

        String fieldName = field.getName();

        if (fieldName.equals(primaryKey) && primaryKeyType.equals("VARCHAR")) {
            field.addAnnotation(idAnnotation);
            field.addAnnotation(generatedValueAnnotation1);
            field.addAnnotation(genericGeneratorAnnotation);
        } else if (fieldName.equals(primaryKey) && primaryKeyType.equals("INT")) {
            field.addAnnotation(idAnnotation);
            field.addAnnotation(generatedValueAnnotation2);
        }
    }

    private void addRepositoryAnnotation(Interface interfaze, IntrospectedTable introspectedTable) {
        interfaze.addImportedType(typeRepository);

        TableConfiguration tableConfiguration = introspectedTable.getTableConfiguration();

        String domainObjectName = tableConfiguration.getDomainObjectName();
        String alias = domainObjectName == null ? tableConfiguration.getTableName() : domainObjectName;
        String aliasAnnotation = String.format("@Repository", alias);

        interfaze.addAnnotation(aliasAnnotation);
    }
}
