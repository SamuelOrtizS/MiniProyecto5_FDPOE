package univalle.fdpoe;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.TreeMap;

public class Ventana extends JFrame {
    private JTabbedPane tpMain;
    private JPanel panel1;
    private JTabbedPane tpGestion;
    private JTable tablaVentasMensuales;
    private JComboBox comboDetalleFacturas;
    private JTable tablaDetalleFacturas;
    private JComboBox comboMarcaProductos;
    private JTable tablaProductos;
    private JTable tablaFacturasMayo;
    private JTextField textFieldIDProductoGestion;
    private JTextField textFieldDescripcionProducto;
    private JComboBox comboIDMarcaGestionProductos;
    private JButton crearButton;
    private JButton actualizarButton;
    private JButton verButton;
    private JButton borrarButton;
    private JTextField textFieldIDMarca;
    private JTextField textFieldNombreMarca;
    private JTextField textFieldFacturaVenta;
    private JSpinner spinnerYear;
    private JSpinner spinnerMonth;
    private JSpinner spinnerDay;
    private JSpinner spinnerHours;
    private JSpinner spinnerMinutes;
    private JTextField textFieldIDDetalleFactura;
    private JComboBox comboIDFactura;
    private JComboBox comboIDProducto;
    private JSpinner spinnerCantidad;
    private JSpinner spinnerValor;
    private JScrollPane panelVentasMensuales;
    private JPanel panelProductos;
    private JScrollPane panelFacturasMayo;
    private JScrollPane scrollProductos;
    private JPanel panelGestionMarcas;
    private JPanel panelGestionProductos;
    private JPanel panelGestionFacturas;
    private JPanel panelGestionDetalleFacturas;
    private TreeMap<Integer, Producto> productoTreeMap = new TreeMap<>();
    private TreeMap<Integer, Marca> marcaTreeMap = new TreeMap<>();
    private TreeMap<Integer, FacturaVenta> facturaVentaTreeMap = new TreeMap<>();
    private TreeMap<Integer, DetalleFactura> detalleFacturaTreeMap = new TreeMap<>();

    public Ventana() {
        super("Sistema CRUD");
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //Se configuran los componentes de la GUI
        setupComponents();
        //Se configuran los escuchadores de la GUI
        setupListeners();
        //Se leen los datos guardados en sesiones previas
        // leerDatosGuardados();
    }

    /**
     * Se configuran las propiedades de los diferentes controles de la GUI
     */
    private void setupComponents(){
        comboIDMarcaGestionProductos.setEditable(true);
        comboMarcaProductos.setEditable(true);
        comboDetalleFacturas.setEditable(true);
        comboIDProducto.setEditable(true);
        comboIDFactura.setEditable(true);

        SpinnerNumberModel modeloYear = new SpinnerNumberModel();
        modeloYear.setValue(2023);
        modeloYear.setMaximum(3000);
        modeloYear.setMinimum(2000);
        spinnerYear.setModel(modeloYear);

        SpinnerNumberModel modeloMonth = new SpinnerNumberModel();
        modeloMonth.setValue(1);
        modeloMonth.setMaximum(12);
        modeloMonth.setMinimum(1);
        spinnerMonth.setModel(modeloMonth);

        SpinnerNumberModel modeloDay = new SpinnerNumberModel();
        modeloDay.setValue(1);
        modeloDay.setMinimum(1);
        modeloDay.setMaximum(31);
        spinnerDay.setModel(modeloDay);

        SpinnerNumberModel modeloHoras = new SpinnerNumberModel();
        modeloHoras.setValue(0);
        modeloHoras.setMinimum(0);
        modeloHoras.setMaximum(23);
        spinnerHours.setModel(modeloHoras);

        SpinnerNumberModel modeloMinutos = new SpinnerNumberModel();
        modeloMinutos.setValue(0);
        modeloMinutos.setMinimum(0);
        modeloMinutos.setMaximum(59);
        spinnerMinutes.setModel(modeloMinutos);
    }

    /**
     * Se configuran los escuchadores de los controles de la GUI
     */
    private void setupListeners(){
        //Se actualizan los diferentes modelos necesarios al momento que se va a interactuar con ellos
        panelVentasMensuales.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                cargarVentasMensuales();
            }

            @Override
            public void focusLost(FocusEvent e) {
                cargarVentasMensuales();
            }
        });
        comboMarcaProductos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarProductos();
            }
        });
        panelFacturasMayo.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                cargarFacturasMayo();
            }

            @Override
            public void focusLost(FocusEvent e) {
                cargarFacturasMayo();
            }
        });
        comboDetalleFacturas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDetalleFacturas();
            }
        });
        panelGestionProductos.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                cargarComboIDMarcas();
            }

            @Override
            public void focusLost(FocusEvent e) {
                cargarComboIDMarcas();
            }
        });
        panelGestionDetalleFacturas.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                cargarComboIDFacturas();
                cargarComboIDProductos();
            }

            @Override
            public void focusLost(FocusEvent e) {
                cargarComboIDFacturas();
                cargarComboIDProductos();
            }
        });

        //Se configuran las acciones de los diferentes botones, asi como los modelos relacionados a estos
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (panelGestionProductos.isShowing()) crearProducto();
                else if (panelGestionMarcas.isShowing()) crearMarca();
                else if (panelGestionFacturas.isShowing()) crearFacturaVenta();
                else if (panelGestionDetalleFacturas.isShowing()) crearDetalleFactura();
                cargarVentasMensuales();
                cargarProductos();
                cargarFacturasMayo();
                cargarDetalleFacturas();
                // guardarDatos();
            }
        });
        verButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (panelGestionProductos.isShowing()) verProducto();
                else if (panelGestionMarcas.isShowing()) verMarca();
                else if (panelGestionFacturas.isShowing()) verFacturaVenta();
                else if (panelGestionDetalleFacturas.isShowing()) verDetalleFactura();
            }
        });
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (panelGestionProductos.isShowing()) actualizarProducto();
                else if (panelGestionMarcas.isShowing()) actualizarMarca();
                else if (panelGestionFacturas.isShowing()) actualizarFactura();
                else if (panelGestionDetalleFacturas.isShowing()) actualizarDetalleFactura();
                cargarVentasMensuales();
                cargarProductos();
                cargarFacturasMayo();
                cargarDetalleFacturas();
                // guardarDatos();
            }
        });
        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (panelGestionProductos.isShowing()) borrarProducto();
                else if (panelGestionMarcas.isShowing()) borrarMarca();
                else if (panelGestionFacturas.isShowing()) borrarFactura();
                else if (panelGestionDetalleFacturas.isShowing()) borrarDetalleFactura();
                cargarVentasMensuales();
                cargarProductos();
                cargarFacturasMayo();
                cargarDetalleFacturas();
                // guardarDatos();
            }
        });
    }

    /**
     * Actualiza la tabla de ventas mensuales
     */
    private void cargarVentasMensuales() {
        DefaultTableModel modeloVentasMensuales = new DefaultTableModel();

        modeloVentasMensuales.addColumn("Mes");
        modeloVentasMensuales.addColumn("Ventas");
        modeloVentasMensuales.addColumn("Total");
        int contadorEnero = 0, totalEnero = 0, contadorFebrero = 0,totalFebrero = 0,contadorMarzo = 0,totalMarzo = 0,contadorAbril = 0,totalAbril = 0,contadorMayo = 0,totalMayo = 0,contadorJunio = 0,totalJunio = 0, contadorJulio = 0,totalJulio = 0,contadorAgosto = 0,totalAgosto = 0,contadorSeptiembre = 0,totalSeptiembre = 0,contadorOctubre = 0,totalOctubre = 0,contadorNoviembre = 0,totalNoviembre = 0,contadorDiciembre = 0,totalDiciembre = 0;
        for (Map.Entry<Integer,FacturaVenta> entry : facturaVentaTreeMap.entrySet()){
            FacturaVenta facturaVenta = entry.getValue();
            switch (facturaVenta.getFechaFacturaVenta().getMonthValue()) {
                case 1 -> {
                    contadorEnero += 1;
                    for (Map.Entry<Integer, DetalleFactura> recorridoDetalleFactura : detalleFacturaTreeMap.entrySet()) {
                        DetalleFactura detalleFactura = recorridoDetalleFactura.getValue();
                        if (detalleFactura.getIdFacturaVenta() == facturaVenta.getIdFacturaVenta())
                            totalEnero += (detalleFactura.getCantidadProductos() * detalleFactura.getValorProducto());
                    }
                }
                case 2 -> {
                    contadorFebrero += 1;
                    for (Map.Entry<Integer, DetalleFactura> recorridoDetalleFactura : detalleFacturaTreeMap.entrySet()) {
                        DetalleFactura detalleFactura = recorridoDetalleFactura.getValue();
                        if (detalleFactura.getIdFacturaVenta() == facturaVenta.getIdFacturaVenta())
                            totalFebrero += (detalleFactura.getCantidadProductos() * detalleFactura.getValorProducto());
                    }
                }
                case 3 -> {
                    contadorMarzo += 1;
                    for (Map.Entry<Integer, DetalleFactura> recorridoDetalleFactura : detalleFacturaTreeMap.entrySet()) {
                        DetalleFactura detalleFactura = recorridoDetalleFactura.getValue();
                        if (detalleFactura.getIdFacturaVenta() == facturaVenta.getIdFacturaVenta())
                            totalMarzo += (detalleFactura.getCantidadProductos() * detalleFactura.getValorProducto());
                    }
                }
                case 4 -> {
                    contadorAbril += 1;
                    for (Map.Entry<Integer, DetalleFactura> recorridoDetalleFactura : detalleFacturaTreeMap.entrySet()) {
                        DetalleFactura detalleFactura = recorridoDetalleFactura.getValue();
                        if (detalleFactura.getIdFacturaVenta() == facturaVenta.getIdFacturaVenta())
                            totalAbril += (detalleFactura.getCantidadProductos() * detalleFactura.getValorProducto());
                    }
                }
                case 5 -> {
                    contadorMayo += 1;
                    for (Map.Entry<Integer, DetalleFactura> recorridoDetalleFactura : detalleFacturaTreeMap.entrySet()) {
                        DetalleFactura detalleFactura = recorridoDetalleFactura.getValue();
                        if (detalleFactura.getIdFacturaVenta() == facturaVenta.getIdFacturaVenta())
                            totalMayo += (detalleFactura.getCantidadProductos() * detalleFactura.getValorProducto());
                    }
                }
                case 6 -> {
                    contadorJunio += 1;
                    for (Map.Entry<Integer, DetalleFactura> recorridoDetalleFactura : detalleFacturaTreeMap.entrySet()) {
                        DetalleFactura detalleFactura = recorridoDetalleFactura.getValue();
                        if (detalleFactura.getIdFacturaVenta() == facturaVenta.getIdFacturaVenta())
                            totalJunio += (detalleFactura.getCantidadProductos() * detalleFactura.getValorProducto());
                    }
                }
                case 7 -> {
                    contadorJulio += 1;
                    for (Map.Entry<Integer, DetalleFactura> recorridoDetalleFactura : detalleFacturaTreeMap.entrySet()) {
                        DetalleFactura detalleFactura = recorridoDetalleFactura.getValue();
                        if (detalleFactura.getIdFacturaVenta() == facturaVenta.getIdFacturaVenta())
                            totalJulio += (detalleFactura.getCantidadProductos() * detalleFactura.getValorProducto());
                    }
                }
                case 8 -> {
                    contadorAgosto += 1;
                    for (Map.Entry<Integer, DetalleFactura> recorridoDetalleFactura : detalleFacturaTreeMap.entrySet()) {
                        DetalleFactura detalleFactura = recorridoDetalleFactura.getValue();
                        if (detalleFactura.getIdFacturaVenta() == facturaVenta.getIdFacturaVenta())
                            totalAgosto += (detalleFactura.getCantidadProductos() * detalleFactura.getValorProducto());
                    }
                }
                case 9 -> {
                    contadorSeptiembre += 1;
                    for (Map.Entry<Integer, DetalleFactura> recorridoDetalleFactura : detalleFacturaTreeMap.entrySet()) {
                        DetalleFactura detalleFactura = recorridoDetalleFactura.getValue();
                        if (detalleFactura.getIdFacturaVenta() == facturaVenta.getIdFacturaVenta())
                            totalSeptiembre += (detalleFactura.getCantidadProductos() * detalleFactura.getValorProducto());
                    }
                }
                case 10 -> {
                    contadorOctubre += 1;
                    for (Map.Entry<Integer, DetalleFactura> recorridoDetalleFactura : detalleFacturaTreeMap.entrySet()) {
                        DetalleFactura detalleFactura = recorridoDetalleFactura.getValue();
                        if (detalleFactura.getIdFacturaVenta() == facturaVenta.getIdFacturaVenta())
                            totalOctubre += (detalleFactura.getCantidadProductos() * detalleFactura.getValorProducto());
                    }
                }
                case 11 -> {
                    contadorNoviembre += 1;
                    for (Map.Entry<Integer, DetalleFactura> recorridoDetalleFactura : detalleFacturaTreeMap.entrySet()) {
                        DetalleFactura detalleFactura = recorridoDetalleFactura.getValue();
                        if (detalleFactura.getIdFacturaVenta() == facturaVenta.getIdFacturaVenta())
                            totalNoviembre += (detalleFactura.getCantidadProductos() * detalleFactura.getValorProducto());
                    }
                }
                case 12 -> {
                    contadorDiciembre += 1;
                    for (Map.Entry<Integer, DetalleFactura> recorridoDetalleFactura : detalleFacturaTreeMap.entrySet()) {
                        DetalleFactura detalleFactura = recorridoDetalleFactura.getValue();
                        if (detalleFactura.getIdFacturaVenta() == facturaVenta.getIdFacturaVenta())
                            totalDiciembre += (detalleFactura.getCantidadProductos() * detalleFactura.getValorProducto());
                    }
                }
            }
        }

        modeloVentasMensuales.addRow(new Object[]{"Enero",contadorEnero,totalEnero});
        modeloVentasMensuales.addRow(new Object[]{"Febrero",contadorFebrero,totalFebrero});
        modeloVentasMensuales.addRow(new Object[]{"Marzo",contadorMarzo,totalMarzo});
        modeloVentasMensuales.addRow(new Object[]{"Abril",contadorAbril,totalAbril});
        modeloVentasMensuales.addRow(new Object[]{"Mayo",contadorMayo,totalMayo});
        modeloVentasMensuales.addRow(new Object[]{"Junio",contadorJunio,totalJunio});
        modeloVentasMensuales.addRow(new Object[]{"Julio",contadorJulio,totalJulio});
        modeloVentasMensuales.addRow(new Object[]{"Agosto",contadorAgosto,totalAgosto});
        modeloVentasMensuales.addRow(new Object[]{"Septiembre",contadorSeptiembre,totalSeptiembre});
        modeloVentasMensuales.addRow(new Object[]{"Octubre",contadorOctubre,totalOctubre});
        modeloVentasMensuales.addRow(new Object[]{"Noviembre",contadorNoviembre,totalNoviembre});
        modeloVentasMensuales.addRow(new Object[]{"Diciembre",contadorDiciembre,totalDiciembre});

        tablaVentasMensuales.setModel(modeloVentasMensuales);
    }

    /**
     * Actualiza la tabla de productos segun una marca seleccionada por el usuario
     */
    private void cargarProductos() {

        DefaultTableModel modeloProductos = new DefaultTableModel();

        modeloProductos.addColumn("ID");
        modeloProductos.addColumn("Descripción");

        int idMarcaSeleccionada;

        String nombreMarca = String.valueOf(comboMarcaProductos.getSelectedItem());

        for (Map.Entry<Integer,Marca> entry : marcaTreeMap.entrySet()){
            Marca recorridoMarca = entry.getValue();
            if (nombreMarca.equals(recorridoMarca.getNombreMarca())) {
                idMarcaSeleccionada = recorridoMarca.getIdMarca();
                for (Map.Entry<Integer,Producto> recorridoProducto : productoTreeMap.entrySet()){
                    Producto producto = recorridoProducto.getValue();
                    if (idMarcaSeleccionada == producto.getIdMarca()) {
                        modeloProductos.addRow(new Object[]{producto.getIdProducto(),producto.getDescripcionProducto()});
                    }
                }
                break;
            }
        }

        tablaProductos.setModel(modeloProductos);
    }

    /**
     * Actualiza la tabla que contiene todas las facturas del mes de Mayo
     */
    private void cargarFacturasMayo() {
        DefaultTableModel modeloFacturasMayo = new DefaultTableModel();

        modeloFacturasMayo.addColumn("ID");
        modeloFacturasMayo.addColumn("Fecha");
        modeloFacturasMayo.addColumn("Hora");
        modeloFacturasMayo.addColumn("Total");

        for (Map.Entry<Integer,FacturaVenta> entry : facturaVentaTreeMap.entrySet()){
            FacturaVenta recorridoFactura = entry.getValue();
            if (recorridoFactura.getFechaFacturaVenta().getMonthValue() == 5) {
                int idFactura = recorridoFactura.getIdFacturaVenta();
                LocalDate fechaFactura = recorridoFactura.getFechaFacturaVenta();
                LocalTime horaFactura = recorridoFactura.getHoraFacturaVenta();
                int totalFactura = 0;
                for (Map.Entry<Integer, DetalleFactura> recorridoDetalleFactura : detalleFacturaTreeMap.entrySet()) {
                    DetalleFactura detalleFactura = recorridoDetalleFactura.getValue();
                    if (detalleFactura.getIdFacturaVenta() == recorridoFactura.getIdFacturaVenta())
                        totalFactura += (detalleFactura.getCantidadProductos() * detalleFactura.getValorProducto());
                }
                modeloFacturasMayo.addRow(new Object[]{idFactura,fechaFactura.toString(),horaFactura.toString(),totalFactura});
            }
        }

        tablaFacturasMayo.setModel(modeloFacturasMayo);
    }

    /**
     * Actualiza la tabla que contiene los detalles de una factura seleccionada por el usuario
     */
    private void cargarDetalleFacturas() {
        int idFacturaSeleccionada;
        try {
            idFacturaSeleccionada = (int) comboDetalleFacturas.getSelectedItem();

            DefaultTableModel modeloDetalleFacturas = new DefaultTableModel();

            modeloDetalleFacturas.addColumn("Dato");
            modeloDetalleFacturas.addColumn("Valor");

            for (Map.Entry<Integer,FacturaVenta> entry : facturaVentaTreeMap.entrySet()){
                FacturaVenta recorridoFactura = entry.getValue();
                if (recorridoFactura.getIdFacturaVenta() == idFacturaSeleccionada) {
                    modeloDetalleFacturas.addRow(new Object[]{"ID",idFacturaSeleccionada});
                    LocalDate fechaFactura;
                    fechaFactura = recorridoFactura.getFechaFacturaVenta();
                    modeloDetalleFacturas.addRow(new Object[]{"Fecha", fechaFactura});

                    LocalTime horaFactura;
                    horaFactura = recorridoFactura.getHoraFacturaVenta();
                    modeloDetalleFacturas.addRow(new Object[]{"Hora",horaFactura});

                    int subtotalFactura = 0, totalFactura = 0;

                    for (Map.Entry<Integer, DetalleFactura> recorridoDetalleFactura : detalleFacturaTreeMap.entrySet()) {
                        DetalleFactura detalleFactura = recorridoDetalleFactura.getValue();
                        if (detalleFactura.getIdFacturaVenta() == recorridoFactura.getIdFacturaVenta())
                            for (Map.Entry<Integer, Producto> recorridoProducto : productoTreeMap.entrySet()) {
                                Producto producto = recorridoProducto.getValue();
                                if (detalleFactura.getIdProducto() == producto.getIdProducto()) {
                                    modeloDetalleFacturas.addRow(new Object[]{"Producto","("+producto.getIdProducto()+")"+producto.getDescripcionProducto()});

                                    int cantidadProducto = detalleFactura.getCantidadProductos();
                                    modeloDetalleFacturas.addRow(new Object[]{"Cantidad",cantidadProducto});

                                    int valorProducto = detalleFactura.getValorProducto();
                                    modeloDetalleFacturas.addRow(new Object[]{"Valor",valorProducto});

                                    subtotalFactura += (cantidadProducto * valorProducto);
                                    totalFactura += (cantidadProducto * valorProducto);
                                }
                            }
                    }
                    modeloDetalleFacturas.addRow(new Object[]{"Sub-Total", subtotalFactura});
                    modeloDetalleFacturas.addRow(new Object[]{"Total", totalFactura});
                    break;
                }
            }
            tablaDetalleFacturas.setModel(modeloDetalleFacturas);
        } catch (Exception e){

        }
    }

    /**
     * Actualiza los ComboBox relacionados a las marcas guardadas
     */
    private void cargarComboIDMarcas() {
        DefaultComboBoxModel<String> modeloMarcas = new DefaultComboBoxModel<>();
        for (Map.Entry<Integer,Marca> entry : marcaTreeMap.entrySet()){
            Marca marca = entry.getValue();
            modeloMarcas.addElement(marca.getNombreMarca());
        }
        comboIDMarcaGestionProductos.setModel(modeloMarcas);
        comboMarcaProductos.setModel(modeloMarcas);
    }

    /**
     * Actualiza los ComboBox relacionados a las facturas guardadas
     */
    private void cargarComboIDFacturas() {
        DefaultComboBoxModel<Integer> modeloFacturas = new DefaultComboBoxModel<>();
        for (Map.Entry<Integer,FacturaVenta> entry : facturaVentaTreeMap.entrySet()){
            FacturaVenta facturaVenta = entry.getValue();
            modeloFacturas.addElement(facturaVenta.getIdFacturaVenta());
        }
        comboIDFactura.setModel(modeloFacturas);
        comboDetalleFacturas.setModel(modeloFacturas);
    }

    /**
     * Actualiza los ComboBox relacionados a los productos guardados
     */
    private void cargarComboIDProductos() {
        DefaultComboBoxModel<String> modeloProductos = new DefaultComboBoxModel<>();
        for (Map.Entry<Integer,Producto> entry : productoTreeMap.entrySet()){
            Producto producto = entry.getValue();
            modeloProductos.addElement(producto.getDescripcionProducto());
        }
        comboIDProducto.setModel(modeloProductos);
    }

    /**
     * Crea un objeto de la clase Producto y lo guarda en el TreeMap correspondiente, haciendo las verificaciones necesarias
     */
    private void crearProducto() {
        Producto producto = new Producto();
        int idProducto, idMarca;

        try {
            idProducto = Integer.parseInt(textFieldIDProductoGestion.getText());
            producto.setIdProducto(idProducto);
        } catch (Exception e) {
            if (e.getClass().equals(NumberFormatException.class)){
                JOptionPane.showMessageDialog(null, "Por favor ingrese un numero en el campo \"ID\"");
            }
        }

        String descripcionProducto;
        descripcionProducto = textFieldDescripcionProducto.getText();
        if (!descripcionProducto.equals("")){
            producto.setDescripcionProducto(descripcionProducto);
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese una descripcion del producto");
        }

        String nombreMarca = String.valueOf(comboIDMarcaGestionProductos.getSelectedItem());

        for (Map.Entry<Integer,Marca> entry : marcaTreeMap.entrySet()){
            Marca recorridoMarca = entry.getValue();
            if (nombreMarca.equals(recorridoMarca.getNombreMarca())) {
                idMarca = recorridoMarca.getIdMarca();
                producto.setIdMarca(idMarca);
                break;
            }
        }

        if (productoTreeMap.isEmpty()) {
            productoTreeMap.put(producto.getIdProducto(),producto);
        } else {
            if (!productoTreeMap.containsKey(producto.getIdProducto())){
                productoTreeMap.put(producto.getIdProducto(),producto);
            } else {
                JOptionPane.showMessageDialog(null,"Ya existe un producto con esa ID, por favor ingrese una ID diferente.");
            }
        }
        cargarComboIDProductos();
    }

    /**
     * Crea un objeto de la clase Marca y lo guarda en el TreeMap correspondiente, haciendo las verificaciones necesarias
     */
    private void crearMarca() {
        Marca marca = new Marca();
        int idMarca;

        try {
            idMarca = Integer.parseInt(textFieldIDMarca.getText());
            marca.setIdMarca(idMarca);
        } catch (Exception e) {
            if (e.getClass().equals(NumberFormatException.class)){
                JOptionPane.showMessageDialog(null, "Por favor ingrese un numero en el campo \"ID\"");
            }
        }

        String nombre = textFieldNombreMarca.getText();
        marca.setNombreMarca(nombre);

        if (marcaTreeMap.isEmpty()) {
            marcaTreeMap.put(marca.getIdMarca(),marca);
        } else {
            if (!marcaTreeMap.containsKey(marca.getIdMarca())){
                boolean nombreRepetido = false;
                for (Map.Entry<Integer,Marca> entry : marcaTreeMap.entrySet()){
                    Marca recorridoMarca = entry.getValue();
                    if (marca.getNombreMarca().equals(recorridoMarca.getNombreMarca())) {
                        nombreRepetido = true;
                        break;
                    }
                }
                if (!nombreRepetido){
                    marcaTreeMap.put(marca.getIdMarca(),marca);
                } else {
                    JOptionPane.showMessageDialog(null,"Ya existe una marca con ese nombre, por favor ingrese un nombre diferente.");
                }
            } else {
                JOptionPane.showMessageDialog(null,"Ya existe una marca con esa ID, por favor ingrese una ID diferente.");
            }
        }
        cargarComboIDMarcas();
    }

    /**
     * Crea un objeto de la clase FacturaVenta y lo guarda en el TreeMap correspondiente, haciendo las verificaciones necesarias
     */
    private void crearFacturaVenta() {
        FacturaVenta factura = new FacturaVenta();
        int idFacturaVenta;

        try {
            idFacturaVenta = Integer.parseInt(textFieldFacturaVenta.getText());
            factura.setIdFacturaVenta(idFacturaVenta);
        } catch (Exception e) {
            if (e.getClass().equals(NumberFormatException.class)){
                JOptionPane.showMessageDialog(null, "Por favor ingrese un numero en el campo \"ID\"");
            }
        }


        int _year = (int) spinnerYear.getValue();
        int _month = (int) spinnerMonth.getValue();
        int _day = (int) spinnerDay.getValue();

        LocalDate fecha = LocalDate.of(_year,_month,_day);

        int _hours = (int) spinnerHours.getValue();
        int _minutes = (int) spinnerMinutes.getValue();

        LocalTime hora = LocalTime.of(_hours,_minutes);

        factura.setFechaFacturaVenta(fecha);
        factura.setHoraFacturaVenta(hora);

        if (facturaVentaTreeMap.isEmpty()) {
            facturaVentaTreeMap.put(factura.getIdFacturaVenta(),factura);
        } else {
            if (!facturaVentaTreeMap.containsKey(factura.getIdFacturaVenta())){
                facturaVentaTreeMap.put(factura.getIdFacturaVenta(),factura);
            } else {
                JOptionPane.showMessageDialog(null,"Ya existe una factura con esa ID, por favor ingrese una ID diferente.");
            }
        }
        cargarComboIDFacturas();
    }

    /**
     * Crea un objeto de la clase DetalleFactura y lo guarda en el TreeMap correspondiente, haciendo las verificaciones necesarias
     */
    private void crearDetalleFactura() {
        DetalleFactura detalleFactura = new DetalleFactura();
        int idDetalleFactura, idFacturaVenta, idProducto, cantidadProductos, valorProducto;

        try {
            idDetalleFactura = Integer.parseInt(textFieldIDDetalleFactura.getText());
            detalleFactura.setIdDetalleFactura(idDetalleFactura);
        } catch (Exception e) {
            if (e.getClass().equals(NumberFormatException.class)){
                JOptionPane.showMessageDialog(null, "Por favor ingrese un numero en el campo \"ID\"");
            }
        }

        try {
            idFacturaVenta = Integer.parseInt(comboIDFactura.getSelectedItem().toString());
            detalleFactura.setIdFacturaVenta(idFacturaVenta);
        } catch (Exception e) {

        }

        String descripcionProducto = String.valueOf(comboIDProducto.getSelectedItem());

        for (Map.Entry<Integer,Producto> entry : productoTreeMap.entrySet()){
            Producto recorridoProducto = entry.getValue();
            if (descripcionProducto.equals(recorridoProducto.getDescripcionProducto())) {
                idProducto = recorridoProducto.getIdProducto();
                detalleFactura.setIdProducto(idProducto);
                break;
            }
        }

        cantidadProductos = (int) spinnerCantidad.getValue();
        detalleFactura.setCantidadProductos(cantidadProductos);

        valorProducto = (int) spinnerValor.getValue();
        detalleFactura.setValorProducto(valorProducto);

        if (detalleFacturaTreeMap.isEmpty()) {
            detalleFacturaTreeMap.put(detalleFactura.getIdDetalleFactura(),detalleFactura);
        } else {
            if (!detalleFacturaTreeMap.containsKey(detalleFactura.getIdDetalleFactura())){
                detalleFacturaTreeMap.put(detalleFactura.getIdDetalleFactura(),detalleFactura);
            } else {
                JOptionPane.showMessageDialog(null,"Ya existe un detalle de factura con esa ID, por favor ingrese una ID diferente.");
            }
        }

    }

    /**
     * Busca y muestra un objeto de la clase Producto en el TreeMap correspondiente, haciendo las verificaciones necesarias
     */
    private void verProducto(){
        int idProducto;
        try {
            idProducto = Integer.parseInt(textFieldIDProductoGestion.getText());

            if (productoTreeMap.isEmpty()) {
                JOptionPane.showMessageDialog(null,"No existe ningun producto guardado");
            } else if (!productoTreeMap.containsKey(idProducto)) {
                    JOptionPane.showMessageDialog(null,"No existe ningun producto guardado con la id dada");
            } else {
                for (Map.Entry<Integer, Producto> entry : productoTreeMap.entrySet()) {
                    Producto producto = entry.getValue();
                    if (idProducto == producto.getIdProducto()) {
                        textFieldDescripcionProducto.setText(producto.getDescripcionProducto());
                        for (Map.Entry<Integer, Marca> recorridoMarca : marcaTreeMap.entrySet()) {
                            Marca marca = recorridoMarca.getValue();
                            if (producto.getIdMarca() == marca.getIdMarca()) {
                                comboIDMarcaGestionProductos.setSelectedItem(marca.getNombreMarca());
                            }
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            if (e.getClass().equals(NumberFormatException.class)){
                JOptionPane.showMessageDialog(null, "Por favor ingrese un numero en el campo \"ID\"");
            }
        }
    }

    /**
     * Busca y muestra un objeto de la clase Marca en el TreeMap correspondiente, haciendo las verificaciones necesarias
     */
    private void verMarca(){
        int idMarca;
        try {
            idMarca = Integer.parseInt(textFieldIDMarca.getText());

            if (marcaTreeMap.isEmpty()) {
                JOptionPane.showMessageDialog(null,"No existe ninguna marca guardada");
            } else if (!marcaTreeMap.containsKey(idMarca)) {
                JOptionPane.showMessageDialog(null,"No existe ninguna marca guardada con la id dada");
            } else {
                for (Map.Entry<Integer, Marca> entry : marcaTreeMap.entrySet()) {
                    Marca marca = entry.getValue();
                    if (idMarca == marca.getIdMarca()) {
                        textFieldNombreMarca.setText(marca.getNombreMarca());
                        break;
                    }
                }
            }
        } catch (Exception e) {
            if (e.getClass().equals(NumberFormatException.class)){
                JOptionPane.showMessageDialog(null, "Por favor ingrese un numero en el campo \"ID\"");
            }
        }
    }

    /**
     * Busca y muestra un objeto de la clase FacturaVenta en el TreeMap correspondiente, haciendo las verificaciones necesarias
     */
    private void verFacturaVenta(){
        int idFacturaVenta;
        try {
            idFacturaVenta = Integer.parseInt(textFieldFacturaVenta.getText());

            if (facturaVentaTreeMap.isEmpty()) {
                JOptionPane.showMessageDialog(null,"No existe ninguna factura guardada");
            } else if (!facturaVentaTreeMap.containsKey(idFacturaVenta)) {
                JOptionPane.showMessageDialog(null,"No existe ninguna factura guardada con la id dada");
            } else {
                for (Map.Entry<Integer, FacturaVenta> entry : facturaVentaTreeMap.entrySet()) {
                    FacturaVenta facturaVenta = entry.getValue();
                    if (idFacturaVenta == facturaVenta.getIdFacturaVenta()) {
                        spinnerYear.setValue(facturaVenta.getFechaFacturaVenta().getYear());
                        spinnerMonth.setValue(facturaVenta.getFechaFacturaVenta().getMonthValue());
                        spinnerDay.setValue(facturaVenta.getFechaFacturaVenta().getDayOfMonth());

                        spinnerHours.setValue(facturaVenta.getHoraFacturaVenta().getHour());
                        spinnerMinutes.setValue(facturaVenta.getHoraFacturaVenta().getMinute());
                        break;
                    }
                }
            }
        } catch (Exception e) {
            if (e.getClass().equals(NumberFormatException.class)){
                JOptionPane.showMessageDialog(null, "Por favor ingrese un numero en el campo \"ID\"");
            }
        }
    }

    /**
     * Busca y muestra un objeto de la clase DetalleFactura en el TreeMap correspondiente, haciendo las verificaciones necesarias
     */
    private void verDetalleFactura(){
        int idDetalleFactura;
        try {
            idDetalleFactura = Integer.parseInt(textFieldIDDetalleFactura.getText());

            if (detalleFacturaTreeMap.isEmpty()) {
                JOptionPane.showMessageDialog(null,"No existe ningun detalle de factura guardado");
            } else if (!detalleFacturaTreeMap.containsKey(idDetalleFactura)) {
                JOptionPane.showMessageDialog(null,"No existe ningun detalle de factura guardado con la id dada");
            } else {
                for (Map.Entry<Integer, DetalleFactura> entry : detalleFacturaTreeMap.entrySet()) {
                    DetalleFactura detalleFactura = entry.getValue();
                    if (idDetalleFactura == detalleFactura.getIdDetalleFactura()) {
                        comboIDFactura.setSelectedItem(detalleFactura.getIdDetalleFactura());

                        for (Map.Entry<Integer, Producto> recorridoProducto : productoTreeMap.entrySet()) {
                            Producto producto = recorridoProducto.getValue();
                            if (detalleFactura.getIdProducto() == producto.getIdProducto()) {
                                comboIDProducto.setSelectedItem(producto.getDescripcionProducto());
                                break;
                            }
                        }

                        spinnerCantidad.setValue(detalleFactura.getCantidadProductos());
                        spinnerValor.setValue(detalleFactura.getValorProducto());

                        break;
                    }
                }
            }
        } catch (Exception e) {
            if (e.getClass().equals(NumberFormatException.class)){
                JOptionPane.showMessageDialog(null, "Por favor ingrese un numero en el campo \"ID\"");
            }
        }
    }

    /**
     * Actualiza y guarda un objeto de la clase Producto en el TreeMap correspondiente, haciendo las verificaciones necesarias
     */
    private void actualizarProducto(){
        int idProducto;
        try {
            idProducto = Integer.parseInt(textFieldIDProductoGestion.getText());

            if (productoTreeMap.isEmpty()) {
                JOptionPane.showMessageDialog(null,"No existe ningun producto guardado");
            } else if (!productoTreeMap.containsKey(idProducto)) {
                JOptionPane.showMessageDialog(null,"No existe ningun producto guardado con la id dada");
            } else {
                Producto productoActualizado = new Producto();
                productoActualizado.setIdProducto(idProducto);
                productoTreeMap.remove(idProducto);
                String descripcionProducto;
                descripcionProducto = textFieldDescripcionProducto.getText();
                if (!descripcionProducto.equals("")){
                    productoActualizado.setDescripcionProducto(descripcionProducto);
                } else {
                    JOptionPane.showMessageDialog(null, "Ingrese una descripcion del producto");
                }

                String nombreMarca = String.valueOf(comboIDMarcaGestionProductos.getSelectedItem());

                for (Map.Entry<Integer,Marca> entry : marcaTreeMap.entrySet()){
                    Marca recorridoMarca = entry.getValue();
                    if (nombreMarca.equals(recorridoMarca.getNombreMarca())) {
                        int idMarca = recorridoMarca.getIdMarca();
                        productoActualizado.setIdMarca(idMarca);
                        break;
                    }
                }
                productoTreeMap.put(idProducto,productoActualizado);

            }
        } catch (Exception e) {
            if (e.getClass().equals(NumberFormatException.class)){
                JOptionPane.showMessageDialog(null, "Por favor ingrese un numero en el campo \"ID\"");
            }
        }
        cargarComboIDProductos();
    }

    /**
     * Actualiza y guarda un objeto de la clase Marca en el TreeMap correspondiente, haciendo las verificaciones necesarias
     */
    private void actualizarMarca(){
        int idMarca;
        try {
            idMarca = Integer.parseInt(textFieldIDMarca.getText());

            if (marcaTreeMap.isEmpty()) {
                JOptionPane.showMessageDialog(null,"No existe ninguna marca guardada");
            } else if (!marcaTreeMap.containsKey(idMarca)) {
                JOptionPane.showMessageDialog(null,"No existe ninguna marca guardada con la id dada");
            } else {
                Marca marcaActualizada = new Marca();
                marcaActualizada.setIdMarca(idMarca);

                marcaTreeMap.remove(idMarca);

                String nombre = textFieldNombreMarca.getText();
                marcaActualizada.setNombreMarca(nombre);

                boolean nombreRepetido = false;
                for (Map.Entry<Integer,Marca> entry : marcaTreeMap.entrySet()){
                    Marca recorridoMarca = entry.getValue();
                    if (marcaActualizada.getNombreMarca().equals(recorridoMarca.getNombreMarca())) {
                        nombreRepetido = true;
                        break;
                    }
                }
                if (!nombreRepetido){
                    marcaTreeMap.put(marcaActualizada.getIdMarca(),marcaActualizada);
                } else {
                    JOptionPane.showMessageDialog(null,"Ya existe una marca con ese nombre, por favor ingrese un nombre diferente.");
                }
            }
        } catch (Exception e) {
            if (e.getClass().equals(NumberFormatException.class)){
                JOptionPane.showMessageDialog(null, "Por favor ingrese un numero en el campo \"ID\"");
            }
        }
        cargarComboIDMarcas();
    }

    /**
     * Actualiza y guarda un objeto de la clase FacturaVenta en el TreeMap correspondiente, haciendo las verificaciones necesarias
     */
    private void actualizarFactura(){
        int idFacturaVenta;
        try {
            idFacturaVenta = Integer.parseInt(textFieldFacturaVenta.getText());

            if (facturaVentaTreeMap.isEmpty()) {
                JOptionPane.showMessageDialog(null,"No existe ninguna factura guardada");
            } else if (!facturaVentaTreeMap.containsKey(idFacturaVenta)) {
                JOptionPane.showMessageDialog(null,"No existe ninguna factura guardada con la id dada");
            } else {
                FacturaVenta facturaActualizada = new FacturaVenta();
                facturaActualizada.setIdFacturaVenta(idFacturaVenta);

                facturaVentaTreeMap.remove(idFacturaVenta);

                int _year = (int) spinnerYear.getValue();
                int _month = (int) spinnerMonth.getValue();
                int _day = (int) spinnerDay.getValue();

                LocalDate fecha = LocalDate.of(_year,_month,_day);

                int _hours = (int) spinnerHours.getValue();
                int _minutes = (int) spinnerMinutes.getValue();

                LocalTime hora = LocalTime.of(_hours,_minutes);

                facturaActualizada.setFechaFacturaVenta(fecha);
                facturaActualizada.setHoraFacturaVenta(hora);

                facturaVentaTreeMap.put(facturaActualizada.getIdFacturaVenta(),facturaActualizada);
            }
        } catch (Exception e) {
            if (e.getClass().equals(NumberFormatException.class)){
                JOptionPane.showMessageDialog(null, "Por favor ingrese un numero en el campo \"ID\"");
            }
        }
        cargarComboIDFacturas();
    }

    /**
     * Actualiza y guarda un objeto de la clase DetalleFactura en el TreeMap correspondiente, haciendo las verificaciones necesarias
     */
    private void actualizarDetalleFactura(){
        int idDetalleFactura;
        try {
            idDetalleFactura = Integer.parseInt(textFieldIDDetalleFactura.getText());

            if (detalleFacturaTreeMap.isEmpty()) {
                JOptionPane.showMessageDialog(null,"No existe ningun detalle de factura guardado");
            } else if (!detalleFacturaTreeMap.containsKey(idDetalleFactura)) {
                JOptionPane.showMessageDialog(null,"No existe ningun detalle de factura guardado con la id dada");
            } else {
                DetalleFactura detalleActualizado = new DetalleFactura();
                detalleActualizado.setIdDetalleFactura(idDetalleFactura);

                detalleFacturaTreeMap.remove(idDetalleFactura);

                int idFacturaVenta, idProducto, cantidadProductos, valorProducto;

                try {
                    idFacturaVenta = Integer.parseInt(comboIDFactura.getSelectedItem().toString());
                    detalleActualizado.setIdFacturaVenta(idFacturaVenta);
                } catch (Exception e) {

                }

                String descripcionProducto = String.valueOf(comboIDProducto.getSelectedItem());

                for (Map.Entry<Integer,Producto> entry : productoTreeMap.entrySet()){
                    Producto recorridoProducto = entry.getValue();
                    if (descripcionProducto.equals(recorridoProducto.getDescripcionProducto())) {
                        idProducto = recorridoProducto.getIdProducto();
                        detalleActualizado.setIdProducto(idProducto);
                        break;
                    }
                }

                cantidadProductos = (int) spinnerCantidad.getValue();
                detalleActualizado.setCantidadProductos(cantidadProductos);

                valorProducto = (int) spinnerValor.getValue();
                detalleActualizado.setValorProducto(valorProducto);

                detalleFacturaTreeMap.put(detalleActualizado.getIdDetalleFactura(),detalleActualizado);
            }
        } catch (Exception e) {
            if (e.getClass().equals(NumberFormatException.class)){
                JOptionPane.showMessageDialog(null, "Por favor ingrese un numero en el campo \"ID\"");
            }
        }
    }

    /**
     * Busca y elimina un objeto de la clase Producto en el TreeMap correspondiente, haciendo las verificaciones necesarias
     */
    private void borrarProducto() {
        int idProducto;
        try {
            idProducto = Integer.parseInt(textFieldIDProductoGestion.getText());

            if (productoTreeMap.isEmpty()) {
                JOptionPane.showMessageDialog(null,"No existe ningun producto guardado");
            } else if (!productoTreeMap.containsKey(idProducto)) {
                JOptionPane.showMessageDialog(null,"No existe ningun producto guardado con la id dada");
            } else {
                productoTreeMap.remove(idProducto);
            }
        } catch (Exception e) {
            if (e.getClass().equals(NumberFormatException.class)){
                JOptionPane.showMessageDialog(null, "Por favor ingrese un numero en el campo \"ID\"");
            }
        }
    }

    /**
     * Busca y elimina un objeto de la clase Marca en el TreeMap correspondiente, haciendo las verificaciones necesarias
     */
    private void borrarMarca() {
        int idMarca;
        try {
            idMarca = Integer.parseInt(textFieldIDMarca.getText());

            if (marcaTreeMap.isEmpty()) {
                JOptionPane.showMessageDialog(null,"No existe ninguna marca guardada");
            } else if (!marcaTreeMap.containsKey(idMarca)) {
                JOptionPane.showMessageDialog(null,"No existe ninguna marca guardada con la id dada");
            } else {
                marcaTreeMap.remove(idMarca);
            }
        } catch (Exception e) {
            if (e.getClass().equals(NumberFormatException.class)){
                JOptionPane.showMessageDialog(null, "Por favor ingrese un numero en el campo \"ID\"");
            }
        }
    }

    /**
     * Busca y elimina un objeto de la clase FacturaVenta en el TreeMap correspondiente, haciendo las verificaciones necesarias
     */
    private void borrarFactura() {
        int idFacturaVenta;
        try {
            idFacturaVenta = Integer.parseInt(textFieldFacturaVenta.getText());

            if (facturaVentaTreeMap.isEmpty()) {
                JOptionPane.showMessageDialog(null,"No existe ninguna factura guardada");
            } else if (!facturaVentaTreeMap.containsKey(idFacturaVenta)) {
                JOptionPane.showMessageDialog(null,"No existe ninguna factura guardada con la id dada");
            } else {
                facturaVentaTreeMap.remove(idFacturaVenta);
            }
        } catch (Exception e) {
            if (e.getClass().equals(NumberFormatException.class)){
                JOptionPane.showMessageDialog(null, "Por favor ingrese un numero en el campo \"ID\"");
            }
        }
    }

    /**
     * Busca y elimina un objeto de la clase DetalleFactura en el TreeMap correspondiente, haciendo las verificaciones necesarias
     */
    private void borrarDetalleFactura() {
        int idDetalleFactura;
        try {
            idDetalleFactura = Integer.parseInt(textFieldIDDetalleFactura.getText());

            if (detalleFacturaTreeMap.isEmpty()) {
                JOptionPane.showMessageDialog(null,"No existe ningun detalle de factura guardado");
            } else if (!detalleFacturaTreeMap.containsKey(idDetalleFactura)) {
                JOptionPane.showMessageDialog(null,"No existe ningun detalle de factura guardado con la id dada");
            } else {
                detalleFacturaTreeMap.remove(idDetalleFactura);
            }
        } catch (Exception e) {
            if (e.getClass().equals(NumberFormatException.class)){
                JOptionPane.showMessageDialog(null, "Por favor ingrese un numero en el campo \"ID\"");
            }
        }
    }

//    /**
//     * Lee los archivos que contienen los datos guardados y los recupera para ser usados en el programa
//     */
//    private void leerDatosGuardados(){
//        try {
//            String rutaMarcas = "src/main/resources/marcas.txt";
//            FileInputStream guardadoMarcas = new FileInputStream(rutaMarcas);
//            ObjectInputStream entradaMarcas = new ObjectInputStream(guardadoMarcas);
//
//            Marca marca1 = (Marca) entradaMarcas.readObject();
//            Marca marca2 = (Marca) entradaMarcas.readObject();
//            Marca marca3 = (Marca) entradaMarcas.readObject();
//            System.out.println(marca1.getIdMarca()+":"+marca1.getNombreMarca());
//            System.out.println(marca2.getIdMarca()+":"+marca2.getNombreMarca());
//            System.out.println(marca3.getIdMarca()+":"+marca3.getNombreMarca());
//
//        } catch (IOException e) {
//            System.out.println("Error al leer");
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    /**
//     * Guarda los datos ingresados durante la ejecucion del programa para ser usados en siguientes sesiones
//     */
//    private void guardarDatos() {
//        try {
//            String rutaMarcas = "src/main/resources/marcas.txt";
//            FileOutputStream archivoMarcas = new FileOutputStream(rutaMarcas);
//            ObjectOutputStream salidaMarcas = new ObjectOutputStream(archivoMarcas);
//
//            for (Map.Entry<Integer, Marca> recorridoMarcas : marcaTreeMap.entrySet()) {
//                salidaMarcas.writeObject(recorridoMarcas.getValue());
//                salidaMarcas.flush();
//            }
//
//            salidaMarcas.close();
//        } catch (IOException e) {
//            System.out.println("Error al escribir");
//            e.printStackTrace();
//        }
//    }
}
