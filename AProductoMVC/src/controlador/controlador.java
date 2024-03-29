package controlador;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
//se importa modelo e interfaz
import modelo.modelo;
import vista.interfaz;
/**
 * @author Mouse
 */
public class controlador implements ActionListener,MouseListener{

    /** instancia a nuestra interfaz de usuario*/
    interfaz vista ;
    /** instancia a nuestro modelo */
    modelo modelo = new modelo();

    /** Se declaran en un ENUM las acciones que se realizan desde la
     * interfaz de usuario VISTA y posterior ejecución desde el controlador
     */
    public enum AccionMVC
    {
        __VER_PRODUCTOS,
        __AGREGAR_PRODUCTO,
        __ELIMINAR_PRODUCTO
    }

    /** Constrcutor de clase
     * @param vista Instancia de clase interfaz
     */
    public controlador( interfaz vista )
    {
        this.vista = vista;
    }

    /** Inicia el skin y las diferentes variables que se utilizan */
    public void iniciar()
    {
        // Skin tipo WINDOWS
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(vista);
            vista.setVisible(true);
        } catch (UnsupportedLookAndFeelException ex) {}
          catch (ClassNotFoundException ex) {}
          catch (InstantiationException ex) {}
          catch (IllegalAccessException ex) {}

        //declara una acción y añade un escucha al evento producido por el componente
        this.vista.__VER_PRODUCTOS.setActionCommand( "__VER_PRODUCTOS" );
        this.vista.__VER_PRODUCTOS.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vista.__AGREGAR_PRODUCTO.setActionCommand( "__AGREGAR_PRODUCTO" );
        this.vista.__AGREGAR_PRODUCTO.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vista.__ELIMINAR_PRODUCTO.setActionCommand( "__ELIMINAR_PRODUCTO" );
        this.vista.__ELIMINAR_PRODUCTO.addActionListener(this);

        //añade e inicia el jtable con un DefaultTableModel vacio
        this.vista.__tabla_producto.addMouseListener(this);
        this.vista.__tabla_producto.setModel( new DefaultTableModel() );
    }

    //Eventos que suceden por el mouse
    public void mouseClicked(MouseEvent e) {
        if( e.getButton()== 1)//boton izquierdo
        {
             int fila = this.vista.__tabla_producto.rowAtPoint(e.getPoint());
             if (fila > -1){                
                this.vista.__id_producto.setText( String.valueOf( this.vista.__tabla_producto.getValueAt(fila, 0) ));
                this.vista.__nombre.setText( String.valueOf( this.vista.__tabla_producto.getValueAt(fila, 1) ));
                this.vista.__precio.setText( String.valueOf( this.vista.__tabla_producto.getValueAt(fila, 2) ));
                this.vista.__cantidad.setText( String.valueOf( this.vista.__tabla_producto.getValueAt(fila, 3) ));
             }
        }
    }

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) { }
 
    //Control de eventos de los controles que tienen definido un "ActionCommand"
    public void actionPerformed(ActionEvent e) {

    switch ( AccionMVC.valueOf( e.getActionCommand() ) )
        {
            case __VER_PRODUCTOS:
                //obtiene del modelo los registros en un DefaultTableModel y lo asigna en la vista
                this.vista.__tabla_producto.setModel( this.modelo.getTablaProducto() );
                break;
            case __AGREGAR_PRODUCTO:
                //añade un nuevo registro
                if ( this.modelo.NuevoProducto(
                        this.vista.__id_producto.getText(),
                        this.vista.__nombre.getText() ,
                        this.vista.__precio.getText(),
                        this.vista.__cantidad.getText() ) )
                {
                    this.vista.__tabla_producto.setModel( this.modelo.getTablaProducto() );
                    JOptionPane.showMessageDialog(vista,"Exito: Nuevo registro agregado.");
                    this.vista.__id_producto.setText("");
                    this.vista.__nombre.setText("") ;
                    this.vista.__precio.setText("0");
                    this.vista.__cantidad.setText("0") ;
                }
                else //ocurrio un error
                    JOptionPane.showMessageDialog(vista,"Error: Los datos son incorrectos.");
                break;
            case __ELIMINAR_PRODUCTO:
                if ( this.modelo.EliminarProducto( this.vista.__id_producto.getText() ) )
                {
                    this.vista.__tabla_producto.setModel( this.modelo.getTablaProducto() );
                    JOptionPane.showMessageDialog(vista,"Exito: Registro eliminado.");
                    this.vista.__id_producto.setText("");
                    this.vista.__nombre.setText("") ;
                    this.vista.__precio.setText("0");
                    this.vista.__cantidad.setText("0") ;
                }
                break;       
        }
    }

}
