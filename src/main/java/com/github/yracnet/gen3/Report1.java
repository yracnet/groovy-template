/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.gen3;

import com.github.yracnet.gen.spec.Util;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author wyujra
 */
public class Report1 {
    
    public static String toLiteral(String text){
        String ts [] = text.split("_|\\s");
        String x = "";
        for(int i=0;i<ts.length;i++){
            String a  =ts[i];
            ts[i] = a.toUpperCase().charAt(0) + a.toLowerCase().substring(1);
            x = x + ts[i] +" ";
        }
        return x.trim();
    }

    public static void main(String[] args) throws SQLException {

        String sql[] = {
            "SELECT \n"
            + "C.CODIGO, \n"
            + "PC.CODIGO_POSTULACION, \n"
            + "PC.ESTADO_POSTULACION, \n"
            + "C.TIPO, \n"
            + "C.PUESTO, \n"
            + "C.DESCRIPCION, \n"
            + "C.OBJETIVO, \n"
            + "C.FECHA_CONCLUSION, \n"
            + "C.FECHA_PUBLICACION, \n"
            + "PC.FECHA_POSTULACION, \n"
            + "P.APELLIDO_MATERNO, \n"
            + "P.APELLIDO_PATERNO, \n"
            + "P.PRIMER_NOMBRE,\n"
            + "P.SEGUNDO_NOMBRE,\n"
            + "P.LUGAR_NACIMIENTO,\n"
            + "P.NACIONALIDAD,\n"
            + "P.TIPO_DOCUMENTO,\n"
            + "P.NRO_DOCUMENTO,\n"
            + "P.EXT_DOCUMENTO,\n"
            + "P.FECHA_NACIMIENTO,\n"
            + "P.GENERO,\n"
            + "PC.ESTADO_CIVIL, \n"
            + "PC.LUGAR_RESIDENCIA, \n"
            + "PC.TELEFONO_CELULAR, \n"
            + "PC.TELEFONO_DOMICILIO, \n"
            + "PC.NOMBRE_REFERENCIA, \n"
            + "PC.TELEFONO_REFERENCIA,\n"
            + "PC.SALARIO_EXPECTATIVA, \n"
            + "PC.SALARIO_PERCIBIDO,  \n"
            + "PC.ID, \n"
            + "PC.ID_CONVOCATORIA, \n"
            + "PC.ID_POSTULANTE\n"
            + "FROM MRP_POSTULACION PC \n"
            + "LEFT JOIN MRP_POSTULANTE P ON P.ID = PC.ID_POSTULANTE\n"
            + "LEFT JOIN MRP_CONVOCATORIA C ON C.ID = PC.ID_CONVOCATORIA\n"
            + "WHERE PC.ID = 2",
            "SELECT\n"
            + "BCB.TIPO_RELACION,\n"
            + "BCB.NOMBRE_CARGO,\n"
            + "BCB.FECHA_DESDE,\n"
            + "BCB.FECHA_HASTA,\n"
            + "BCB.NOTA_EVALUACION\n"
            + "FROM MRP_EXPERIENCIA_BCB BCB\n"
            + "WHERE BCB.ID_POSTULACION = #ID_POSTULACION#",
            "SELECT  \n"
            + "HU.NOMBRE_UNIDAD,\n"
            + "HU.NIVEL_ESTUDIO,\n"
            + "HU.LUGAR_ESTUDIO,\n"
            + "HU.CON_DIPLOMA,   \n"
            + "HU.NUMERO_DIPLOMA\n"
            + "FROM MRP_FORMACION_HUMANISTICO HU\n"
            + "WHERE HU.ID_POSTULACION = #ID_POSTULACION#",
            "SELECT \n"
            + "CO.TIPO,\n"
            + "CO.CATEGORIA,\n"
            + "CO.NUMERO_LICENCIA \n"
            + "FROM MRP_FORMACION_CONDUCCION CO\n"
            + "WHERE CO.ID_POSTULACION = #ID_POSTULACION#",
            "SELECT \n"
            + "FS.NOMBRE_TITULO, \n"
            + "FS.DESCRIPCION_TITULO,\n"
            + "FS.NOMBRE_CENTRO_ESTUDIO,\n"
            + "FS.LUGAR_CENTRO_ESTUDIO,\n"
            + "FS.CARGA_HORARIA, \n"
            + "FS.TIPO_TITULO,\n"
            + "FS.EMISION_TITULO,  \n"
            + "FS.NUMERO_TITULO \n"
            + "FROM MRP_FORMACION_SUPERIOR FS\n"
            + "WHERE FS.ID_POSTULACION = #ID_POSTULACION#\n"
            + "AND FS.TIPO_FORMACION = \"SECRETARIADO\"",
            "SELECT \n"
            + "FS.NOMBRE_TITULO, \n"
            + "FS.DESCRIPCION_TITULO,\n"
            + "FS.NOMBRE_CENTRO_ESTUDIO,\n"
            + "FS.LUGAR_CENTRO_ESTUDIO,\n"
            + "FS.CARGA_HORARIA, \n"
            + "FS.TIPO_TITULO,\n"
            + "FS.EMISION_TITULO,  \n"
            + "FS.NUMERO_TITULO \n"
            + "FROM MRP_FORMACION_SUPERIOR FS\n"
            + "WHERE FS.ID_POSTULACION = #ID_POSTULACION#\n"
            + "AND FS.TIPO_FORMACION != \"SECRETARIADO\"",
            "SELECT  \n"
            + "FU.UNIVERSIDAD_NOMBRE,\n"
            + "FU.UNIVERSIDAD_LUGAR,  \n"
            + "FU.UNIVERSIDAD_CARRERA, \n"
            + "FU.UNIVERSIDAD_INGRESO,\n"
            + "FU.CURSADO_GESTION, \n"
            + "FU.CURSADO_GRADO, \n"
            + "FU.CON_CERTIFICADO, \n"
            + "FU.EMISION_CERTIFICADO\n"
            + "FROM MRP_FORMACION_UNIVERSITARIA FU\n"
            + "WHERE FU.ID_POSTULACION = #ID_POSTULACION#",
            "SELECT \n"
            + "FP.TITULO_PROFESIONAL,\n"
            + "FP.UNIVERSIDAD_NOMBRE,\n"
            + "FP.DESCRIPCION_PROFESION,\n"
            + "FP.UNIVERSIDAD_LUGAR, \n"
            + "FP.UNIVERSIDAD_EGRESO, \n"
            + "FP.TIPO_TITULO,   \n"
            + "FP.EMISION_TITULO, \n"
            + "FP.NUMERO_TITULO\n"
            + "FROM MRP_FORMACION_PROFESIONAL FP\n"
            + "WHERE FP.ID_POSTULACION = #ID_POSTULACION#",
            "SELECT \n"
            + "FP.TITULO_PROFESIONAL,\n"
            + "FP.CON_REGISTRO1, \n"
            + "FP.REGISTRO1_FECHA, \n"
            + "FP.REGISTRO1_NUMERO, \n"
            + "FP.REGISTRO1_ORGANIZACION, \n"
            + "FP.CON_REGISTRO2, \n"
            + "FP.REGISTRO2_FECHA, \n"
            + "FP.REGISTRO2_NUMERO, \n"
            + "FP.REGISTRO2_ORGANIZACION\n"
            + "FROM MRP_FORMACION_PROFESIONAL FP\n"
            + "WHERE FP.ID_POSTULACION = #ID_POSTULACION#",
            "SELECT   \n"
            + "FP.TITULO_OBTENIDO, \n"
            + "FP.TIPO_CENTRO_ESTUDIO,\n"
            + "FP.NOMBRE_CENTRO_ESTUDIO, \n"
            + "FP.LUGAR_CENTRO_ESTUDIO, \n"
            + "FP.FECHA_DESDE, \n"
            + "FP.FECHA_HASTA, \n"
            + "FP.ESTADO_ACTUAL,\n"
            + "FP.NUMERO_TITULO, \n"
            + "FP.CARGA_HORARIA \n"
            + "FROM MRP_FORMACION_POSTGRADO FP\n"
            + "WHERE FP.ID_POSTULACION = #ID_POSTULACION#\n"
            + "AND FP.NIVEL_ESTUDIO IN (\"DIPLOMADO\", \"ESPECIALIDAD\")",
            "SELECT   \n"
            + "FP.TITULO_OBTENIDO, \n"
            + "FP.TIPO_CENTRO_ESTUDIO,\n"
            + "FP.NOMBRE_CENTRO_ESTUDIO, \n"
            + "FP.LUGAR_CENTRO_ESTUDIO, \n"
            + "FP.FECHA_DESDE, \n"
            + "FP.FECHA_HASTA, \n"
            + "FP.ESTADO_ACTUAL,\n"
            + "FP.NUMERO_TITULO, \n"
            + "FP.CARGA_HORARIA \n"
            + "FROM MRP_FORMACION_POSTGRADO FP\n"
            + "WHERE FP.ID_POSTULACION = #ID_POSTULACION#\n"
            + "AND FP.NIVEL_ESTUDIO IN (\"MAESTRIA\")",
            "SELECT  \n"
            + "FP.TITULO_OBTENIDO, \n"
            + "FP.TIPO_CENTRO_ESTUDIO,\n"
            + "FP.NOMBRE_CENTRO_ESTUDIO, \n"
            + "FP.LUGAR_CENTRO_ESTUDIO, \n"
            + "FP.FECHA_DESDE, \n"
            + "FP.FECHA_HASTA, \n"
            + "FP.ESTADO_ACTUAL,\n"
            + "FP.NUMERO_TITULO, \n"
            + "FP.CARGA_HORARIA \n"
            + "FROM MRP_FORMACION_POSTGRADO FP\n"
            + "WHERE FP.ID_POSTULACION = #ID_POSTULACION#\n"
            + "AND FP.NIVEL_ESTUDIO IN (\"DOCTORADO\")",
            "SELECT  \n"
            + "FC.NOMBRE_EVENTO, \n"
            + "FC.NOMBRE_ORGANIZADOR, \n"
            + "FC.LUGAR_EVENTO, \n"
            + "FC.FECHA_DESDE, \n"
            + "FC.FECHA_HASTA,\n"
            + "FC.CARGA_HORARIA, \n"
            + "FC.CON_CERTIFICADO\n"
            + "FROM MRP_FORMACION_CAPACITACION FC\n"
            + "WHERE FC.ID_POSTULACION = #ID_POSTULACION#",
            "SELECT \n"
            + "EC.NOMBRE_CONOCIMIENTO,\n"
            + "EC.DOMINIO_CONOCIMIENTO, \n"
            + "EC.NIVEL_CONOCIMIENTO\n"
            + "FROM MRP_EXPERIENCIA_CONOCIMIENTO EC\n"
            + "WHERE EC.ID_POSTULACION = #ID_POSTULACION#\n"
            + "AND EC.GRUPO_CONOCIMIENTO = \"IDIOMA\"",
            "SELECT \n"
            + "EC.NOMBRE_CONOCIMIENTO,\n"
            + "EC.DOMINIO_CONOCIMIENTO, \n"
            + "EC.NIVEL_CONOCIMIENTO\n"
            + "FROM MRP_EXPERIENCIA_CONOCIMIENTO EC\n"
            + "WHERE EC.ID_POSTULACION = #ID_POSTULACION#\n"
            + "AND EC.GRUPO_CONOCIMIENTO = \"COMPUTACION\"",
            "SELECT \n"
            + "EL.TIPO_ENTIDAD, \n"
            + "EL.NOMBRE_ENTIDAD, \n"
            + "EL.CARGO_OCUPADO, \n"
            + "EL.FECHA_DESDE, \n"
            + "EL.FECHA_HASTA, \n"
            + "EL.NOMBRE_INMEDIATO_SUPERIOR, \n"
            + "EL.PUESTO_INMEDIATO_SUPERIOR, \n"
            + "EL.TELEFONO_REFERENCIA, \n"
            + "EL.CON_DEPENDIENTES, \n"
            + "EL.NUMERO_DEPENDIENTES, \n"
            + "EL.CON_CERTIFICADO, \n"
            + "EL.DESCRIPCION_FUN1, \n"
            + "EL.DESCRIPCION_FUN2, \n"
            + "EL.DESCRIPCION_FUN3\n"
            + "FROM MRP_EXPERIENCIA_LABORAL EL\n"
            + "WHERE EL.ID_POSTULACION = #ID_POSTULACION#",};
        String names[] = {
            "01-postulante.jrxml",
            "02-bcb.jrxml",
            "03-01-humanistico.jrxml",
            "03-02-conduccion.jrxml",
            "03-03-superior.jrxml",
            "03-04-superior.jrxml",
            "03-05-universitaria.jrxml",
            "03-06-profesional.jrxml",
            "03-07-profesional.jrxml",
            "03-08-postgrado.jrxml",
            "03-09-postgrado.jrxml",
            "03-10-postgrado.jrxml",
            "04-capacitacion.jrxml",
            "05-01-conocimiento.jrxml",
            "05-02-conocimiento.jrxml",
            "06-laboral.jrxml",};
        String url = "jdbc:h2:file:~/h2-database/mrp-persit;AUTO_SERVER=TRUE;DB_CLOSE_ON_EXIT=FALSE";
        Connection conn = DriverManager.getConnection(url, "sa", "sa");
        Statement st = conn.createStatement();


        for (int i = 0; i < sql.length; i++) {
            String q = sql[i];
            String name = names[i];
            File file = new File("/work/dev/bcb-01/R03/019-back/mrp-document/rpt/x/" + name);
            process(file, q, st);
        }
    }

    private static void process(File file, String sql, Statement st) throws SQLException {
        sql = sql.replace("\"", "'");
        
        System.out.println("=========================================================================");
        //System.out.println("--->" + sql);
        String template = TEMPLATE;
        ResultSet rs;
        try {
        String sqlRun = sql.replace("#ID_POSTULACION#", "2");
            rs = st.executeQuery(sqlRun);
        } catch (SQLException e) {
            System.out.println("==> ERROR: " + e.getMessage());
            return;
        }
        ResultSetMetaData data = rs.getMetaData();

        String name = file.getName().replace(".jrxml", "");
        //file = new File(file.getParentFile(), name);
        int l = data.getColumnCount();
        String sqlParam = sql.replace("#ID_POSTULACION#", "$P{P_ID_POSTULACION}");
        template = template.replace("#QUERY_SQL#", sqlParam);
        template = template.replace("#NAME#", name);
        StringBuilder out = new StringBuilder();
        for (int i = 1; i <= l; i++) {
            out.append("	<field name=\"");
            out.append(data.getColumnName(i));
            out.append("\" class=\"");
            out.append(data.getColumnClassName(i));
            out.append("\"/>\n");
            //process(data, i, out);
        }
        template = template.replace("#QUERY_FIELD#", out.toString());
        out = new StringBuilder();
        int y = 0;
        for (int i = 1; i <= l; i++) {
            String text = data.getColumnName(i);
            text = toLiteral(text);
            out.append("			<staticText>\n");
            out.append("				<reportElement style=\"H3X_TEXT\" positionType=\"Float\" x=\"0\" y=\"");
            out.append(y);
            out.append("\" width=\"150\" height=\"15\" />\n");
            out.append("				<text><![CDATA[");
            out.append(text);
            out.append(":]]></text>\n");
            out.append("			</staticText>\n");
            y = y + 15;
        }
        template = template.replace("#Y#", ""+y);
        template = template.replace("#DATA_LABEL#", out.toString());
        out = new StringBuilder();
        y = 0;
        for (int i = 1; i <= l; i++) {
            out.append("			<textField>\n");
            out.append("				<reportElement style=\"D3X_TEXT\" positionType=\"Float\" x=\"150\" y=\"");
            out.append(y);
            out.append("\" width=\"150\" height=\"15\" />\n");
            out.append("				<textFieldExpression><![CDATA[$F{");
            out.append(data.getColumnName(i));
            out.append("}]]></textFieldExpression>\n");
            out.append("			</textField>\n");
            y = y + 15;
        }
        template = template.replace("#DATA_FIELD#", out.toString());
        if (file.exists()) {
            file.delete();
        }
        Util.writeContent(template, file, false, null);
        System.out.println("---->WRITE: " + file);
    }

    static String TEMPLATE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<!-- Created with Jaspersoft Studio version 6.4.0.final using JasperReports Library version 6.4.1  -->\n"
            + "<jasperReport xmlns=\"http://jasperreports.sourceforge.net/jasperreports\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd\" name=\"#NAME#\" pageWidth=\"550\" pageHeight=\"800\" whenNoDataType=\"NoDataSection\" columnWidth=\"550\" leftMargin=\"0\" rightMargin=\"0\" topMargin=\"0\" bottomMargin=\"0\" uuid=\"4eedbb89-b4f6-4469-9ab6-f642a1688cf7\">\n"
            + "	<property name=\"com.jaspersoft.studio.data.sql.tables\" value=\"\"/>\n"
            + "	<property name=\"com.jaspersoft.studio.data.defaultdataadapter\" value=\"DEMO1\"/>\n"
            + "	<property name=\"com.jaspersoft.studio.unit.\" value=\"pixel\"/>\n"
            + "	<property name=\"com.jaspersoft.studio.unit.pageHeight\" value=\"pixel\"/>\n"
            + "	<property name=\"com.jaspersoft.studio.unit.pageWidth\" value=\"pixel\"/>\n"
            + "	<property name=\"com.jaspersoft.studio.unit.topMargin\" value=\"pixel\"/>\n"
            + "	<property name=\"com.jaspersoft.studio.unit.bottomMargin\" value=\"pixel\"/>\n"
            + "	<property name=\"com.jaspersoft.studio.unit.leftMargin\" value=\"pixel\"/>\n"
            + "	<property name=\"com.jaspersoft.studio.unit.rightMargin\" value=\"pixel\"/>\n"
            + "	<property name=\"com.jaspersoft.studio.unit.columnWidth\" value=\"pixel\"/>\n"
            + "	<property name=\"com.jaspersoft.studio.unit.columnSpacing\" value=\"pixel\"/>\n"
            + "	<template><![CDATA[$P{P_REPORT_STYLE}]]></template>\n"
            + "	<subDataset name=\"tableDataset\" uuid=\"f13e6d36-5148-4ecc-bbe3-3035def80980\">\n"
            + "		<queryString>\n"
            + "			<![CDATA[]]>\n"
            + "		</queryString>\n"
            + "	</subDataset>\n"
            + "	<parameter name=\"P_REPORT_STYLE\" class=\"java.lang.String\" isForPrompting=\"false\">\n"
            + "		<defaultValueExpression><![CDATA[\"../Form3360.jrtx\"]]></defaultValueExpression>\n"
            + "	</parameter>\n"
            + "	<parameter name=\"P_ID_POSTULACION\" class=\"java.lang.Integer\" isForPrompting=\"false\">\n"
            + "		<parameterDescription><![CDATA[Id Postulacion]]></parameterDescription>\n"
            + "		<defaultValueExpression><![CDATA[2]]></defaultValueExpression>\n"
            + "	</parameter>\n"
            + "	<queryString language=\"SQL\">\n"
            + "		<![CDATA[\n"
            + "#QUERY_SQL#\n"
            + "]]>\n"
            + "	</queryString>\n"
            + "#QUERY_FIELD#\n"
            + "	<detail>\n"
            + "		<band height=\"#Y#\">\n"
            + "			<property name=\"com.jaspersoft.studio.unit.height\" value=\"pixel\"/>\n"
            + "			<property name=\"com.jaspersoft.studio.layout\" value=\"com.jaspersoft.studio.editor.layout.FreeLayout\"/>\n"
            + "#DATA_LABEL#\n"
            + "#DATA_FIELD#\n"
            + "		</band>\n"
            + "	</detail>\n"
            + "	<noData>\n"
            + "		<band height=\"15\">\n"
            + "			<staticText>\n"
            + "				<reportElement style=\"H3X_TEXT\" positionType=\"Float\" x=\"0\" y=\"0\" width=\"550\" height=\"15\"  uuid=\"fcca4824-f24c-4ebe-a145-81329af15246\"/>\n"
            + "				<text><![CDATA[Sin Registro]]></text>\n"
            + "			</staticText>\n"
            + "		</band>\n"
            + "	</noData>\n"
            + "</jasperReport>";

}
