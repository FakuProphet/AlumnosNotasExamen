/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import DTO.NotasDeAlumnoCurso;
import Modelo.Alumno;
import Modelo.Curso;
import Modelo.Notas;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author alumno
 */
public class Gestor {
    
    
    private Connection con;

	public void abrirConexion() 
	{
		 try {
			String userName = "test";
			String password = "test";
			String url = "jdbc:sqlserver://localhost;databaseName=Alumnos";
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			con = DriverManager.getConnection(url, userName, password);
			System.out.print("Conexion a base de datos abierta");
		} 
		catch (Exception e) {
			System.out.print("Error al abrir conexion");
		}
	}

	public void cerrarConexion() 
	{
		try {
			con.close();
			System.out.println("Conexi�n cerrada");
		} 
		catch (Exception e) 
		{
			System.out.println("Error al cerrar conexi�n");
		 }
	}
	
        
public void  insertarNota(Notas n)
{
   
        try
        {
            abrirConexion();
           String sql="";
           PreparedStatement pstm;
           sql= " insert into notas(legajo,parcial,nota,codCurso) values (?,?,?,?) "; 
           pstm=con.prepareStatement(sql);
            
           pstm.setInt(1, n.getLegajo());
           pstm.setInt(2, n.getParcial());
           pstm.setInt(3, n.getNota());
           pstm.setInt(4, n.getCodCurso());
          pstm.executeUpdate();
                 
            
        
           pstm.close();
        cerrarConexion();
        }   
        
            
            catch (Exception e) 
            {
            System.out.println("error: Nota ya Cargada...");
            }
  
    
}   


public ArrayList<Alumno> getListadoAlumnos()
{
    ArrayList<Alumno> listado= new ArrayList<>();
    
    try
    {
        this.abrirConexion();
        ResultSet salida;
        Statement consulta;
        String sql="select * from alumno";
        consulta=con.createStatement();
        salida=consulta.executeQuery(sql);
        
        while(salida.next())
        {
            int legajo= salida.getInt("legajo");
            String nombre= salida.getString("nombre");
            Alumno nuevo = new Alumno(legajo,nombre);
            listado.add(nuevo);
        }
        
        
                this.cerrarConexion();
                consulta.close();
                salida.close();
    
    }
    catch(Exception e)
    {
        System.out.println("Error al obtener datos de los alumnos");
    }
            
    

    return listado;
}

public ArrayList<Curso> getListadoCurso()
{
    ArrayList<Curso> listado= new ArrayList<>();
    
    try
    {
        this.abrirConexion();
        ResultSet salida;
        Statement consulta;
        String sql="select * from cursos";
        consulta=con.createStatement();
        salida=consulta.executeQuery(sql);
        
        while(salida.next())
        {
            int codigo= salida.getInt("codigo");
            String nombre= salida.getString("nombre");
            String turno=salida.getString("turno");
            Curso nuevo = new Curso(codigo,nombre,turno);
            listado.add(nuevo);
        }
        
        
                this.cerrarConexion();
                consulta.close();
                salida.close();
    
    }
    catch(Exception e)
    {
        System.out.println("Error al obtener datos de los alumnos");
    }
            
    

    return listado;
}


public ArrayList<Notas> getNotas()
{
    ArrayList<Notas> listado= new ArrayList<>();
    
    try
    {
        this.abrirConexion();
        ResultSet salida;
        Statement consulta;
        String sql="select * from notas";
        consulta=con.createStatement();
        salida=consulta.executeQuery(sql);
        
        while(salida.next())
        {
            int leg= salida.getInt(1);
            int parcial= salida.getInt(2);
            int nota=salida.getInt(3);
            int curso=salida.getInt(4);
            Notas nueva= new Notas(leg,parcial,nota,curso);
        }
        
        
                this.cerrarConexion();
                consulta.close();
                salida.close();
    
    }
    catch(Exception e)
    {
        System.out.println("Error al obtener datos ");
    }
            
    

    return listado;
}





public ArrayList <NotasDeAlumnoCurso> getNotasAlumnoConNombreCurso()
  {
      ArrayList <NotasDeAlumnoCurso> listado = new ArrayList <>();
      try
      {
        this.abrirConexion();
        ResultSet salida;
        Statement consulta;
        String sql="select n.nota,a.nombre as alumno,c.nombre\n" +
                    "from alumno a inner join notas n on a.legajo=n.legajo inner join cursos c on c.codigo=n.codCurso\n" +
                    "group by n.nota,a.nombre,c.nombre ";
        consulta=con.createStatement();
        salida=consulta.executeQuery(sql);
        
         while(salida.next())
        { 
            int nota= salida.getInt("nota");
            String nombreAlumno = salida.getString("alumno");
            String nombreCurso=salida.getString("nombre");
            
            NotasDeAlumnoCurso nuevo = new NotasDeAlumnoCurso(nota,nombreAlumno,nombreCurso);
            listado.add(nuevo);
        }
        
        
                this.cerrarConexion();
                consulta.close();
                salida.close();
    
        
        
      }
      catch(Exception e)
      {
          System.out.println("Error:" + e.toString());
      }
      return listado;
  }

       
public float promedioNotasPresentes()
{
    
    float promedio=0f;
    
   try
   {
     this.abrirConexion();
        ResultSet salida;
        Statement consulta;
        String sql="select avg(nota)\n" +
                    "from notas\n" +
                    "where nota>0";
        consulta=con.createStatement();
        salida=consulta.executeQuery(sql);
        
         if(salida.next())
        { 
           promedio=salida.getFloat(1);
        }
        
        
                this.cerrarConexion();
                consulta.close();
                salida.close();
   }
       catch(Exception e)
               {
               System.out.println("error" + e.toString() );
               }
    
   
    
    return promedio;
}
    
    //sobre los q no rindieron
public float porcentajeAusentes()
{
    int cNotasCero=0;
    int cTotal=0;
    float porcentaje=0f;
    
    try
    {
        this.abrirConexion();
        ResultSet salida;
        Statement consulta;
        String sql="select count(*) from notas where nota=0";
        consulta=con.createStatement();
        salida=consulta.executeQuery(sql);
        
        if(salida.next())
        {
        cNotasCero=salida.getInt(1);
        }
        /********************************/
        ResultSet salida2;
        Statement consulta2;
        String sql2="select count(*) from notas";
        consulta2=con.createStatement();
        salida2=consulta.executeQuery(sql2);
        
        if(salida2.next())
        {
           cTotal=salida2.getInt(1);
        }
         this.cerrarConexion();
                consulta.close();
                salida.close();
                consulta2.close();
                salida2.close();
                
                porcentaje=(float)cNotasCero/cTotal*100;
    }
    catch(Exception e)
            {
                System.out.println("error" +e.toString());
            }
    
    return porcentaje;
}


public String turnoPeorPromedioNotas()
{
    String salidaDatos="";
    int promedioTarde=0;
    int promedioMañana=0;
    int promedioNoche=0;
       try
   {
     this.abrirConexion();
        ResultSet salida;
        Statement consulta;
        //UNA CONSULTA Q ME DEVUELVE EL PROEDIO DE CADA TURNO 
        //USO EL MISMO RESULTSET Y STATEMENT PARA TODO 
        // TENIENDO EN CUENTA DE PONER BIEN LAS CONSULTAS EN ORDEN 
        String sql1="select avg (nota) from notas n inner join cursos c on n.codCurso=c.codigo where turno like 'T'";
                String sql2="select avg (nota) from notas n inner join cursos c on n.codCurso=c.codigo where turno like 'M'";
                        String sql3="select avg (nota) from notas n inner join cursos c on n.codCurso=c.codigo where turno like 'N'";
        consulta=con.createStatement();
        salida=consulta.executeQuery(sql1);
        
         if(salida.next())
        { 
           promedioTarde=salida.getInt(1);
        }
        
         
         
        consulta=con.createStatement();
        salida=consulta.executeQuery(sql2);
         
         
         if(salida.next())
        { 
           promedioMañana=salida.getInt(1);
        }
         
         
           consulta=con.createStatement();
        salida=consulta.executeQuery(sql3);
         
         
         if(salida.next())
        { 
          promedioNoche=salida.getInt(1);
        }
         
         
         
         
         
                this.cerrarConexion();
                consulta.close();
                salida.close();
                
                if(promedioMañana < promedioNoche)
                {
                    if(promedioMañana<promedioTarde)
                    {
                        salidaDatos="el peor turno es el turno -mañana- PROMEDIO:" + promedioMañana;
                    }
                    
                    else
                    {
                        salidaDatos="el peor turno es el turno -tarde- PROMEDIO:" + promedioTarde;
                    }
                }
                else
                {
                        salidaDatos="el peor turno es el turno -noche- PROMEDIO:" + promedioNoche;
                }
                
                
   }
       catch(Exception e)
               {
               System.out.println("error" + e.toString() );
               }
    
    return salidaDatos;
}


}
