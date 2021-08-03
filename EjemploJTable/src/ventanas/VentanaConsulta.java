package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import conexion.Conexion;
import vo.PersonaVo;
import dao.PersonaDao;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import java.awt.Color;

public class VentanaConsulta extends JFrame implements ActionListener {

	private static final long serialVersionUID = -8680456340003162472L;
	private JLabel labelTitulo;
	JTable miTabla1;
	JScrollPane mibarra1;
	private JButton btnActualizar;
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextField txtProfesion;
	private JTextField txtEdad;
	private JTextField txtTelefono;

	/**
	 * constructor de la clase donde se inicializan todos los componentes de la
	 * ventana de registro
	 */
	public VentanaConsulta() {
		getContentPane().setBackground(new Color(128, 128, 128));
		setSize(842, 421);
		setTitle("UNAM : Componentes JTable");
		setLocationRelativeTo(null);
		setResizable(false);
		
		inicializaComponentes();
		construirTabla();
	}

	private void construirTabla() {
		String titulos[]={ "Codigo", "Nombre", "Profesión", "Edad","Telefono" };
		String informacion[][]=obtenerMatriz();
		
		miTabla1=new JTable(informacion,titulos);
		miTabla1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Conexion con = new Conexion();
				try {
					int fila = miTabla1.getSelectedRow();
					int id = Integer.parseInt(miTabla1.getValueAt(fila, 0).toString());
					ResultSet rs;
					Connection conexion = con.getConnection();
					PreparedStatement ps = ((Connection) conexion).prepareStatement("SELECT nombre,edad,profesion,telefono FROM persona WHERE id=?");
					ps.setInt(1, id);
					rs= ps.executeQuery();
					
					while (rs.next()) {
						txtCodigo.setText(String.valueOf(id));
						txtNombre.setText(rs.getString("nombre"));
						txtEdad.setText(rs.getString("edad"));
						txtProfesion.setText(rs.getString("profesion"));
						txtTelefono.setText(rs.getString("telefono"));
					}
				} catch(SQLException e2) {
					JOptionPane.showMessageDialog(null, e.toString());
				}
			}
		});
		miTabla1.setFont(new Font("Montserrat", Font.PLAIN, 12));
		mibarra1.setViewportView(miTabla1);
		
	}

	private String[][] obtenerMatriz() {
		
		PersonaDao miPersonaDao=new PersonaDao();
		ArrayList<PersonaVo>miLista=miPersonaDao.buscarUsuariosConMatriz();
		
		String matrizInfo[][]=new String[miLista.size()][5];
		
		for (int i = 0; i < miLista.size(); i++) {
			matrizInfo[i][0]=miLista.get(i).getIdPersona()+"";
			matrizInfo[i][1]=miLista.get(i).getNombrePersona()+"";
			matrizInfo[i][2]=miLista.get(i).getProfesionPersona()+"";
			matrizInfo[i][3]=miLista.get(i).getEdadPersona()+"";
			matrizInfo[i][4]=miLista.get(i).getTelefonoPersona()+"";
		}
			
		return matrizInfo;
	}

	private void inicializaComponentes() {
		getContentPane().setLayout(null);

		labelTitulo = new JLabel();
		labelTitulo.setForeground(new Color(238, 232, 170));
		labelTitulo.setBounds(428, 20, 400, 30);
		labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitulo.setText("REGISTRO GENERAL");
		labelTitulo.setFont(new Font("Montserrat Black", Font.BOLD, 20));
		getContentPane().add(labelTitulo);
		
		mibarra1=new JScrollPane();
		mibarra1.setFont(new Font("Montserrat Light", Font.PLAIN, 12));
		mibarra1.setBounds(381, 61,431,238);
		getContentPane().add(mibarra1);
		
		btnActualizar = new JButton("Actualizar");
		btnActualizar.setRolloverIcon(new ImageIcon("C:\\Users\\USER\\Downloads\\refresh31.png"));
		btnActualizar.setBackground(Color.LIGHT_GRAY);
		btnActualizar.setDoubleBuffered(true);
		btnActualizar.setSelected(true);
		btnActualizar.setBorderPainted(false);
		btnActualizar.setFocusTraversalKeysEnabled(false);
		btnActualizar.setFocusPainted(false);
		btnActualizar.setFocusable(false);
		btnActualizar.setBorder(null);
		btnActualizar.setIcon(new ImageIcon("C:\\Users\\USER\\Downloads\\refresh3.png"));
		btnActualizar.setFont(new Font("Montserrat SemiBold", Font.PLAIN, 14));
		btnActualizar.setBounds(265, 330, 105, 30);
		getContentPane().add(btnActualizar);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0));
		panel.setBounds(10, 61, 349, 238);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("C\u00F3digo");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Montserrat", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 12, 59, 22);
		panel.add(lblNewLabel);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setForeground(Color.WHITE);
		lblNombre.setFont(new Font("Montserrat", Font.PLAIN, 12));
		lblNombre.setBounds(10, 57, 59, 22);
		panel.add(lblNombre);
		
		JLabel lblProfesion = new JLabel("Profesi\u00F3n");
		lblProfesion.setForeground(Color.WHITE);
		lblProfesion.setFont(new Font("Montserrat", Font.PLAIN, 12));
		lblProfesion.setBounds(10, 99, 59, 22);
		panel.add(lblProfesion);
		
		JLabel lblEdad = new JLabel("Edad");
		lblEdad.setForeground(Color.WHITE);
		lblEdad.setFont(new Font("Montserrat", Font.PLAIN, 12));
		lblEdad.setBounds(10, 141, 59, 22);
		panel.add(lblEdad);
		
		JLabel lblTelfono = new JLabel("Tel\u00E9fono");
		lblTelfono.setForeground(Color.WHITE);
		lblTelfono.setFont(new Font("Montserrat", Font.PLAIN, 12));
		lblTelfono.setBounds(10, 184, 59, 22);
		panel.add(lblTelfono);
		
		txtCodigo = new JTextField();
		txtCodigo.setBackground(Color.WHITE);
		txtCodigo.setBounds(79, 13, 257, 22);
		panel.add(txtCodigo);
		txtCodigo.setColumns(10);
		
		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(79, 58, 257, 22);
		panel.add(txtNombre);
		
		txtProfesion = new JTextField();
		txtProfesion.setColumns(10);
		txtProfesion.setBounds(79, 102, 257, 22);
		panel.add(txtProfesion);
		
		txtEdad = new JTextField();
		txtEdad.setColumns(10);
		txtEdad.setBounds(79, 144, 257, 22);
		panel.add(txtEdad);
		
		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(79, 186, 257, 22);
		panel.add(txtTelefono);
		
		JButton btnInsertar = new JButton("Insertar");
		btnInsertar.setBackground(Color.DARK_GRAY);
		btnInsertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Conexion con = new Conexion();
				int id = Integer.parseInt(txtCodigo.getText());
				String nombre = txtNombre.getText();
				String profesion = txtProfesion.getText();
				int edad = Integer.parseInt(txtEdad.getText());
				int telefono = Integer.parseInt(txtTelefono.getText());
				
				try {
					Connection conexion = con.getConnection();
					PreparedStatement ps = ((Connection) conexion).prepareStatement("INSERT INTO persona (id,nombre,edad,profesion,telefono) VALUES (?,?,?,?,?)");
					ps.setInt(1, id);
					ps.setString(2, nombre);
					ps.setInt(3, edad);
					ps.setString(4, profesion);
					ps.setInt(5, telefono);
					ps.executeUpdate();
					JOptionPane.showMessageDialog(null, "El registro ha sido guardado.");
				} catch(SQLException e3) {
					JOptionPane.showMessageDialog(null, e3.toString());
				}
			}
		});
		btnInsertar.setFont(new Font("Montserrat SemiBold", Font.PLAIN, 14));
		btnInsertar.setBounds(138, 330, 105, 30);
		getContentPane().add(btnInsertar);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.setBackground(Color.DARK_GRAY);
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Conexion con = new Conexion();
				int id = Integer.parseInt(txtCodigo.getText());
				
				try {
					Connection conexion = con.getConnection();
					PreparedStatement ps = ((Connection) conexion).prepareStatement("DELETE FROM persona WHERE id=?");
					ps.setInt(1, id);
					ps.executeUpdate();
					JOptionPane.showMessageDialog(null, "El registro ha sido eliminado.");
				} catch(SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.toString());
					
				}
				
			}
		});
		btnBorrar.setFont(new Font("Montserrat SemiBold", Font.PLAIN, 14));
		btnBorrar.setBounds(397, 330, 98, 30);
		getContentPane().add(btnBorrar);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBackground(Color.DARK_GRAY);
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiar();
			}
		});
		btnLimpiar.setFont(new Font("Montserrat SemiBold", Font.PLAIN, 14));
		btnLimpiar.setBounds(525, 330, 98, 30);
		getContentPane().add(btnLimpiar);
		
		JLabel lblDatos = new JLabel();
		lblDatos.setForeground(new Color(238, 232, 170));
		lblDatos.setBounds(10, 20, 360, 30);
		getContentPane().add(lblDatos);
		lblDatos.setBackground(Color.WHITE);
		lblDatos.setText("DATOS PERSONALES");
		lblDatos.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatos.setFont(new Font("Montserrat Black", Font.BOLD, 20));
		btnActualizar.addActionListener(this);
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==btnActualizar) {
			construirTabla();
		}
	}
	public void limpiar() {
		txtCodigo.setText("");
		txtNombre.setText("");
		txtProfesion.setText("");
		txtEdad.setText("");
		txtTelefono.setText("");
		
	}
}
