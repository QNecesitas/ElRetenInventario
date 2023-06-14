package com.qnecesitas.elreteninventario.database

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

class RoomDatabaseImport : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        //Account
        db.execSQL("INSERT INTO `Account` VALUES( '1234','Administrador'),( '4321','Dependiente')")

        //ShelfS
        db.execSQL("INSERT INTO `ShelfS` VALUES('Cuarto', 2)")

        //ShelfLS
        db.execSQL("INSERT INTO `ShelfLS` VALUES " +
                "('Correas', 1)," +
                "('Estante Madera', 2)," +
                "('Estante Metal', 19)," +
                "('Repisa', 0)")

        //DrawerS
        db.execSQL("INSERT INTO `DrawerS` VALUES" +
                "('Cuarto_Accesorios de motos ', 'Cuarto', 5)," +
                "('Inventario', 'Cuarto', 6)")


        //DrawerLS
        db.execSQL("INSERT INTO `DrawerLS` VALUES" +
                "('6000\n6001\n6002', 'Estante Metal', 3),"+
                "('6003\n6004', 'Estante Metal', 3),"+
                "('6005\n6006', 'Estante Metal', 3),"+
                "('6007', 'Estante Metal', 3),"+
                "('607\n608\n6200', 'Estante Metal', 3),"+
                "('6201', 'Estante Metal', 3),"+
                "('6202\n', 'Estante Metal', 3),"+
                "('6203', 'Estante Metal', 3),"+
                "('6204', 'Estante Metal', 3),"+
                "('6205', 'Estante Metal', 3),"+
                "('6206', 'Estante Metal', 3),"+
                "('6207', 'Estante Metal', 3),"+
                "('6300\n6301', 'Estante Metal', 3),"+
                "('6302', 'Estante Metal', 3),"+
                "('6303', 'Estante Metal', 3),"+
                "('6304', 'Estante Metal', 3),"+
                "('6305', 'Estante Metal', 3),"+
                "('6306', 'Estante Metal', 3),"+
                "('Correas_Tipos', 'Correas', 1),"+
                "('Estante Madera_Arriba', 'Estante Madera', 1),"+
                "('Estante Madera_Retenes', 'Estante Madera', 85),"+
                "('Estante Metal_6006', 'Estante Metal', 0)")



        //SessionS
        db.execSQL("INSERT INTO `SessionS` VALUES"+
                "('Accesorios de Motos', 'Inventario', 41),"+
                "('Cajebolas', 'Inventario', 16),"+
                "('Correas', 'Inventario', 6),"+
                "('Cuarto_Accesorios de motos _AX100', 'Cuarto_Accesorios de motos ', 0),"+
                "('Cuarto_Accesorios de motos _Electricidad ', 'Cuarto_Accesorios de motos', 0),"+
                "('Cuarto_Accesorios de motos _ETZ - MZ 250', 'Cuarto_Accesorios de motos ', 0),"+
                "('Cuarto_Accesorios de motos _GN - 125', 'Cuarto_Accesorios de motos ', 0),"+
                "('Cuarto_Accesorios de motos _Taeko ', 'Cuarto_Accesorios de motos ', 0),"+
                "('Inventario_Pqte de Retenes', 'Inventario', 0),"+
                "('Retenes', 'Inventario', 118),"+
                "('Rolletes', 'Inventario', 1)")



        //SessionLS
        db.execSQL("INSERT INTO `SessionLS` VALUES"+
                "('6000\\n6001\\n6002_Chinas', '6000\\n6001\\n6002', 1)," +
                "('6000\\n6001\\n6002_KFB', '6000\\n6001\\n6002', 0)," +
                "('6000\\n6001\\n6002_SKF', '6000\\n6001\\n6002', 0)," +
                "('6003\\n6004_Chinas', '6003\\6004', 1)," +
                "('6003\\n6004_KFB', '6003\\n6004', 0)," +
                "('6003\\n6004_SKF', '6003\\n6004', 0)," +
                "('6005\\n6006_Chinas', '6005\\n6006', 1)," +
                "('6005\\n6006_KFB', '6005\\n6006', 0)," +
                "('6005\\n6006_SKF', '6005\\n6006', 0)," +
                "('6007_Chinas', '6007', 0)," +
                "('6007_KFB', '6007', 0)," +
                "('6007_SKF', '6007', 0)," +
                "('607\\n608\\n6200_Chinas', '607\\n608\\n6200', 0)," +
                "('607\\n608\\n6200_KFB', '607\\n608\\n6200', 0)," +
                "('607\\n608\\n6200_SKF', '607\\n608\\n6200', 0)," +
                "('6201_Chinas', '6201', 1)," +
                "('6201_KFB', '6201', 0)," +
                "('6201_SKF', '6201', 1)," +
                "('6202\\n_Chinas', '6202\\n', 1)," +
                "('6202\\n_KFB', '6202\\n', 0)," +
                "('6202\\n_SKF', '6202\\n', 0)," +
                "('6203_Chinas', '6203', 1)," +
                "('6203_KFB', '6203', 0)," +
                "('6203_SKF', '6203', 0)," +
                "('6204_Chinas', '6204', 0)," +
                "('6204_KFB', '6204', 1)," +
                "('6205_Chinas', '6205', 1)," +
                "('6205_KFB', '6205', 1)," +
                "('6205_SKF', '6205', 0)," +
                "('6206_Chinas', '6206', 0)," +
                "('6206_KFB', '6206', 1)," +
                "('6206_SKF', '6206', 0)," +
                "('6207_Chinas', '6207', 0)," +
                "('6207_KFB', '6207', 0)," +
                "('6207_SKF', '6207', 0)," +
                "('6300\\n6301_Chinas', '6300\\n6301', 1)," +
                "('6300\\n6301_KFB', '6300\\n6301', 0)," +
                "('6300\\n6301_SKF', '6300\\n6301', 0)," +
                "('6302_Chinas', '6302', 1)," +
                "('6302_KFB', '6302', 0)," +
                "('6302_SKF', '6302', 0)," +
                "('6303_Chinas', '6303', 1)," +
                "('6303_KFB', '6303', 0)," +
                "('6303_SKF', '6303', 0)," +
                "('6304_Chinas', '6304', 0)," +
                "('6304_KFB', '6304', 0)," +
                "('6304_SKF', '6304', 1)," +
                "('6305_Chinas', '6305', 0)," +
                "('6305_KFB', '6305', 0)," +
                "('6305_SKF', '6305', 0)," +
                "('6306_Chinas', '6306', 0)," +
                "('6306_KFB', '6306', 0)," +
                "('6306_SKF', '6306', 0)," +
                "('Correas_Tipos_A', 'Correas_Tipos', 2)," +
                "('Estante Madera_Arriba_Accesorios  de motos', 'Estante Madera_Arriba', 0)," +
                "('Estante Madera_Retenes_1', 'Estante Madera_Retenes', 2)," +
                "('Estante Madera_Retenes_10', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_11', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_12', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_13', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_14', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_15', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_16', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_17', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_18', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_19', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_2', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_20', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_21', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_22', 'Estante Madera_Retenes', 0)," +
                "('Estante Madera_Retenes_23', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_24', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_25', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_26', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_27', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_28', 'Estante Madera_Retenes', 2)," +
                "('Estante Madera_Retenes_29', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_3', 'Estante Madera_Retenes', 4)," +
                "('Estante Madera_Retenes_30', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_31', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_32', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_33', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_34', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_35', 'Estante Madera_Retenes', 2)," +
                "('Estante Madera_Retenes_36', 'Estante Madera_Retenes', 2)," +
                "('Estante Madera_Retenes_37', 'Estante Madera_Retenes', 2)," +
                "('Estante Madera_Retenes_38', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_39', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_4', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_40', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_41', 'Estante Madera_Retenes', 2)," +
                "('Estante Madera_Retenes_42', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_43', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_44', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_45', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_46', 'Estante Madera_Retenes', 3)," +
                "('Estante Madera_Retenes_47', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_48', 'Estante Madera_Retenes', 0)," +
                "('Estante Madera_Retenes_49', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_5', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_50', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_51', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_52', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_53', 'Estante Madera_Retenes', 0)," +
                "('Estante Madera_Retenes_54', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_55', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_56', 'Estante Madera_Retenes', 2)," +
                "('Estante Madera_Retenes_57', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_58', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_59', 'Estante Madera_Retenes', 2)," +
                "('Estante Madera_Retenes_6', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_60', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_61', 'Estante Madera_Retenes', 2)," +
                "('Estante Madera_Retenes_62', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_63', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_64', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_65', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_66', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_67', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_68', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_69', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_7', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_70', 'Estante Madera_Retenes', 1)," +
                "('Estante Madera_Retenes_71', 'Estante Madera_Retenes', 0)," +
                "('Estante Madera_Retenes_72', 'Estante Madera_Retenes', 0)," +
                "('Estante Madera_Retenes_73', 'Estante Madera_Retenes', 0)," +
                "('Estante Madera_Retenes_74', 'Estante Madera_Retenes', 0)," +
                "('Estante Madera_Retenes_75', 'Estante Madera_Retenes', 0)," +
                "('Estante Madera_Retenes_76', 'Estante Madera_Retenes', 0)," +
                "('Estante Madera_Retenes_77', 'Estante Madera_Retenes', 0)," +
                "('Estante Madera_Retenes_79', 'Estante Madera_Retenes', 0)," +
                "('Estante Madera_Retenes_8', 'Estante Madera_Retenes', 4)," +
                "('Estante Madera_Retenes_80', 'Estante Madera_Retenes', 0)," +
                "('Estante Madera_Retenes_81', 'Estante Madera_Retenes', 0)," +
                "('Estante Madera_Retenes_82', 'Estante Madera_Retenes', 0)," +
                "('Estante Madera_Retenes_85', 'Estante Madera_Retenes', 0)," +
                "('Estante Madera_Retenes_86', 'Estante Madera_Retenes', 0)," +
                "('Estante Madera_Retenes_87', 'Estante Madera_Retenes', 0)," +
                "('Estante Madera_Retenes_9', 'Estante Madera_Retenes', 2)," +
                "('Estante Madera_Retenes_Repisa', 'Estante Madera_Retenes', 15)," +
                "('SKF', '6204', 0);")


        //ProductS
        db.execSQL("INSERT INTO `ProductS` VALUES" +
                "('100636', 'Cachimbas Criollas', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'Cachimbas')," +
                "('109993', 'Retén ', 'Retenes', 4, 0, 1000, '', 0, 0, '120x150x10', 'Repisa')," +
                "('113165', 'Retén ', 'Retenes', 4, 0, 650, '', 0, 0, '25x72x8', '70')," +
                "('113480', '6301', 'Cajebolas', 104, 375, 650, 'Centros de moto eléctrica, ax100 trasera', 0, 10, '12x37x12', 'China')," +
                "('117479', 'Retén ', 'Retenes', 4, 0, 650, '', 0, 0, '28x42x7', '46')," +
                "('119425', 'Retén ', 'Retenes', 4, 0, 800, '', 0, 0, '48x73x10', '65')," +
                "('120160', 'Conmutador Integral Negro ', 'Accesorios de Motos', 5, 0, 0, '', 0, 0, '', 'Negro')," +
                "('127666', 'Reten', 'Retenes', 3, 250, 400, '', 0, 1, '7x16x7', '3')," +
                "('134647', 'Retén ', 'Retenes', 1, 0, 600, '', 0, 0, '20x37x8', '9')," +
                "('136655', 'Retén ', 'Retenes', 3, 0, 1000, '', 0, 0, '90x120x10', 'Repisa')," +
                "('137589', 'Retén ', 'Retenes', 4, 0, 700, '', 1, 0, '28x47x8', '46')," +
                "('139186', 'Retén ', 'Retenes', 1, 0, 550, '', 0, 0, '17x47x8', '59')," +
                "('142167', 'Retén ', 'Retenes', 4, 0, 850, '', 0, 0, '70x92x10', 'Repisa')," +
                "('142856', 'Retén ', 'Retenes', 1, 0, 500, '', 0, 0, '15x32x7', '12')," +
                "('143534', 'Retén ', 'Retenes', 5, 0, 700, '', 0, 0, '35x8x58', 'nono')," +
                "('163531', 'Retén ', 'Retenes', 6, 0, 750, '', 0, 0, '16x24x7', '5')," +
                "('166634', 'Retén ', 'Retenes', 3, 0, 850, '', 0, 0, '53x68x10', '67')," +
                "('172274', 'Cable Criollo', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'Cable Criollo')," +
                "('181436', 'Calzo Espro Criollo ETZ', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'ETZ')," +
                "('184609', 'Varilla Freno ETZ (Criolla', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'ETZ Criolla')," +
                "('185396', 'Espejos de ETZ', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'ETZ')," +
                "('187732', 'Retén ', 'Retenes', 4, 0, 850, '', 0, 0, '65x90x12', 'Repisa')," +
                "('188179', 'Retén ', 'Retenes', 1, 0, 0, '', 0, 0, '17x47x10', '59')," +
                "('199770', 'Conmutadores  AX100', 'Accesorios de Motos', 5, 0, 0, '', 0, 0, '', 'AX100')," +
                "('213964', 'Tensores GN-125', 'Accesorios de Motos', 5, 0, 0, '', 0, 0, '', 'Tensores Cadena')," +
                "('221862', 'Retén ', 'Retenes', 4, 0, 700, '', 0, 0, '35.8x68x12', '74')," +
                "('223454', 'Retén ', 'Retenes', 1, 0, 500, '', 0, 0, '15x35x7', '14')," +
                "('232507', 'Retén ', 'Retenes', 3, 0, 700, '', 0, 0, '30x62x8', '69')," +
                "('240398', 'Retén ', 'Retenes', 4, 0, 900, '', 0, 0, '80x105x10', 'Repisa')," +
                "('243150', 'Retén ', 'Retenes', 3, 0, 650, '', 0, 0, '25x50x12', '52')," +
                "('254143', 'Retén ', 'Retenes', 4, 0, 750, '', 0, 0, '41x56x8', '54')," +
                "('256208', 'Retén ', 'Retenes', 1, 0, 500, '', 0, 0, '16x47x10', '49')," +
                "('263820', 'Retén ', 'Retenes', 1, 0, 500, '', 0, 0, '16x30x7', '6')," +
                "('264282', 'Retén ', 'Retenes', 4, 0, 900, '', 0, 0, '75x100x10', '82')," +
                "('271428', 'Retén ', 'Retenes', 1, 0, 400, '', 0, 0, '12x22x7', '20')," +
                "('274910', 'Intermitentes ', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'GN125')," +
                "('277412', 'Retén ', 'Retenes', 1, 0, 600, '', 0, 0, '22x35x7', '42')," +
                "('279922', 'Retén ', 'Retenes', 4, 0, 700, '', 0, 0, '42x68x8', 'Repisa ')," +
                "('283311', '6206', 'Cajebolas', 13, 600, 100, 'Cajebola', 0, 5, '30x62x16', 'KFB')," +
                "('283765', 'Retén ', 'Retenes', 9, 0, 700, '', 0, 0, '30x41x7', '27')," +
                "('302570', '6202', 'Cajebolas', 285, 300, 550, 'Cajebola', 0, 10, '15x35x11', 'China')," +
                "('309022', 'Retén ', 'Retenes', 1, 0, 0, '', 0, 0, '18x32x5.5', '35')," +
                "('316744', 'Retén ', 'Retenes', 4, 0, 700, '', 0, 0, '31x41x7', '39')," +
                "('333629', 'Correa A38', 'Correas', 0, 0, 0, '', 0, 0, '', 'A38')," +
                "('336061', '6000', 'Cajebolas', 15, 250, 450, 'Cajebola', 0, 4, '10x26x8', 'China ')," +
                "('344668', 'Retén ', 'Retenes', 4, 0, 850, '', 0, 0, '55x82x10', '80')," +
                "('364624', '6302', 'Cajebolas', 115, 450, 750, 'Rueda trasera suzuki,taeko,GN125', 0, 10, '15x42x13', 'China')," +
                "('368314', 'Cable cloche de ax100 original', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'AX100 ')," +
                "('375166', 'Retén ', 'Retenes', 4, 0, 700, '', 0, 0, '31x43x10', '38')," +
                "('376697', 'Retén ', 'Retenes', 3, 0, 700, '', 0, 0, '36x52x10', '51')," +
                "('383143', 'Retén ', 'Retenes', 4, 0, 850, '', 0, 0, '60x75x8', '75')," +
                "('385496', 'Vaper - CDI de AX00', 'Accesorios de Motos', 5, 0, 0, '', 0, 0, '', 'Con Cachimba')," +
                "('388754', 'Correa A39', 'Correas', 0, 0, 0, '', 0, 0, '', 'A39')," +
                "('389316', '6005', 'Cajebolas', 61, 520, 850, 'Caña de Suzuki ax100', 0, 19, '25x47x12', 'China')," +
                "('394853', '6201', 'Cajebolas', 7, 500, 850, 'Cajebola', 0, 5, '12x32x10', 'SKF')," +
                "('396305', 'Correa A37', 'Correas', 0, 0, 0, '', 0, 0, '', 'A37')," +
                "('402558', 'Retén ', 'Retenes', 4, 0, 750, '', 0, 0, '42x68x10', 'Repisa ')," +
                "('405344', 'Retén ', 'Retenes', 1, 0, 450, '', 0, 0, '14.8x30x7', '21')," +
                "('406054', 'Filtro aceite de GN125', 'Accesorios de Motos', 15, 300, 550, 'Filtro de aceite de GN125', 0, 5, '', 'Filtro aceite GN')," +
                "('421325', 'Cachimbas Originales ', 'Accesorios de Motos', 5, 0, 0, '', 0, 0, '', 'Cachimbas ')," +
                "('424133', 'Retén ', 'Retenes', 4, 0, 650, '', 0, 0, '25x52x7', '34')," +
                "('427435', 'Retén ', 'Retenes', 4, 0, 850, '', 0, 0, '60x60x10', 'Repisa')," +
                "('438708', 'Retén ', 'Retenes', 1, 0, 600, '', 0, 0, '20x47x10', '9')," +
                "('441538', 'Retén ', 'Retenes', 1, 0, 600, '', 0, 0, '20x32x7', '7')," +
                "('441997', 'Retén ', 'Retenes', 3, 0, 750, '', 0, 0, '40x56x8', '55')," +
                "('443158', '30305', 'Rolletes', 54, 600, 1500, 'Chiquito de Gazella y Borga', 0, 5, '', 'AGL')," +
                "('445969', 'Retén ', 'Retenes', 4, 0, 700, '', 0, 0, '30x40.5x10.5', '25')," +
                "('450987', 'Retén ', 'Retenes', 4, 0, 1000, '', 0, 0, '100x120x10', 'Repisa')," +
                "('469883', 'Intermitentes Criollos Cuadrados', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'ETZ')," +
                "('471931', 'Retén ', 'Retenes', 4, 0, 850, '', 0, 0, '68x95x10', '77')," +
                "('476508', 'Retén ', 'Retenes', 4, 0, 850, '', 0, 0, '68x86x8', '1')," +
                "('480430', 'Retén ', 'Retenes', 1, 0, 600, '', 0, 0, '20x36x7', '16')," +
                "('487106', 'Retén ', 'Retenes', 2, 0, 700, '', 0, 0, '35x45x8', '48')," +
                "('488529', 'Retén ', 'Retenes', 4, 0, 650, '', 0, 0, '26x52x8', '43')," +
                "('494347', 'Retén ', 'Retenes', 4, 0, 700, '', 0, 0, '38x54x8', '60')," +
                "('495546', 'Correa A36', 'Correas', 0, 0, 0, '', 0, 0, '', 'A36')," +
                "('500113', 'Retén ', 'Retenes', 1, 0, 400, '', 0, 0, '10x20x7', '3')," +
                "('505065', 'Filtro de aceite LADA(Grande)', 'Accesorios de Motos', 10, 1200, 1500, 'Filtro grande', 0, 2, '', '')," +
                "('511469', ' 6304', 'Cajebolas', 15, 1400, 1800, 'Cigueñal de ax100', 0, 4, '20x52x15', 'SKF')," +
                "('515591', 'Retén ', 'Retenes', 4, 0, 700, '', 0, 0, '30x52x7', '32')," +
                "('516399', 'Retén ', 'Retenes', 5, 0, 850, '', 0, 0, '51x76x10', 'Repisa')," +
                "('519840', 'Retén ', 'Retenes', 4, 0, 750, '', 0, 0, '42x58x9', '73')," +
                "('523372', 'Retén ', 'Retenes', 1, 0, 600, '', 0, 0, '20x40x8', '45')," +
                "('525699', 'Retén ', 'Retenes', 1, 0, 600, '', 0, 0, '22x36.5x6', '10')," +
                "('532633', 'Anillas Tubo de Escape', 'Accesorios de Motos', 5, 0, 0, '', 0, 0, '', 'AX100')," +
                "('550017', '6201', 'Cajebolas', 128, 300, 550, 'Centro delantero de suzuki ax100 (Banda)', 0, 10, '12x32x10', 'China')," +
                "('556251', 'Retén ', 'Retenes', 1, 0, 550, '', 0, 0, '19.5x35x8', '8')," +
                "('557767', 'Espejos de AX100', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'AX100')," +
                "('558525', 'Retén ', 'Retenes', 2, 0, 700, '', 0, 0, '30x50x7', '50')," +
                "('571812', 'Retén ', 'Retenes', 3, 0, 550, '', 0, 0, '18.9x30x7', '13')," +
                "('584585', 'Retén ', 'Retenes', 2, 0, 500, '', 0, 0, '15x25x7', '4')," +
                "('586761', 'Llave de Gasolina AX100', 'Accesorios de Motos', 5, 0, 0, '', 1, 0, '', '')," +
                "('587038', 'Retén ', 'Retenes', 4, 0, 750, '', 0, 0, '44x60x7', '56')," +
                "('591306', 'Retén ', 'Retenes', 4, 0, 650, '', 0, 0, '27x39x7', '30')," +
                "('595561', 'Retén ', 'Retenes', 4, 0, 800, '', 0, 0, '50x68x7', '71')," +
                "('600172', 'Retén ', 'Retenes', 4, 0, 700, '', 0, 0, '42x62x10', '61')," +
                "('629865', 'Retén ', 'Retenes', 4, 0, 700, '', 0, 0, '32x44x10.5', '29')," +
                "('636113', 'Retén ', 'Retenes', 1, 0, 600, '', 0, 0, '19x30x7', '15')," +
                "('638751', 'Retén ', 'Retenes', 1, 0, 550, '', 0, 0, '18x47x7', '40')," +
                "('639810', 'Retén ', 'Retenes', 1, 0, 550, '', 0, 0, '17x32x8', '1')," +
                "('643749', 'Retén ', 'Retenes', 4, 0, 800, '', 0, 0, '50x65x10 ', '76')," +
                "('646302', 'Cable de Freno AX100', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'AX100')," +
                "('651897', 'Foco trasero', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'AX100')," +
                "('656413', 'Retén ', 'Retenes', 3, 0, 700, '', 0, 0, '35x47x7', '2')," +
                "('659517', 'Encendido de GN125', 'Accesorios de Motos', 3, 7200, 10000, 'GN125', 0, 2, '', 'GN125')," +
                "('665009', 'Retén ', 'Retenes', 4, 0, 850, '', 0, 0, '52x65x8', '79')," +
                "('665462', 'Foco delantero', 'Accesorios de Motos', 5, 0, 3500, '', 0, 2, '', 'AX100')," +
                "('665466', 'Retén ', 'Retenes', 4, 0, 750, '', 0, 0, '42x75x10', '68')," +
                "('666437', 'Tensores Cadena', 'Accesorios de Motos', 5, 0, 0, '', 1, 0, '', 'AX100')," +
                "('679552', 'Conmutador Integral Gris', 'Accesorios de Motos', 5, 0, 0, '', 0, 0, '', 'Gris')," +
                "('689119', 'Retén ', 'Retenes', 2, 0, 850, '', 0, 0, '60x85x10 ', '35')," +
                "('699249', 'Retén ', 'Retenes', 4, 0, 850, '', 0, 0, '60x90x10', 'Repisa')," +
                "('699812', 'Retén ', 'Retenes', 4, 0, 850, '', 0, 0, '52x68x8', 'Repisa')," +
                "('701437', 'Retén ', 'Retenes', 4, 0, 0, '', 0, 0, '18x37x7', '19')," +
                "('702769', 'Filtro de gasolina', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'Filtro Gasolina')," +
                "('705749', 'Retén ', 'Retenes', 2, 0, 700, '', 0, 0, '33.4x63x10', '58')," +
                "('710755', 'llave gasolina Karpaty ', 'Accesorios de Motos', 5, 0, 0, '', 0, 0, '', '')," +
                "('711177', '6205', 'Cajebolas', 12, 700, 1000, 'Cigueñal Suzuki ', 0, 10, '25x52x15', 'China')," +
                "('723126', 'Repisa', 'Retenes', 4, 0, 1000, '', 0, 0, '95x127x14', 'Repisa')," +
                "('723591', 'Correa A40', 'Correas', 0, 0, 0, '', 0, 0, '', 'A40')," +
                "('723788', 'Vaper - CDI de AX00', 'Accesorios de Motos', 5, 0, 0, '', 0, 0, '', 'Sin Cachimba')," +
                "('728478', 'Retén ', 'Retenes', 3, 0, 650, '', 0, 0, '28x40x7', '41')," +
                "('728534', '6004', 'Cajebolas', 33, 450, 700, 'Cajebola', 0, 5, '20x42x12', 'China')," +
                "('733892', 'Retén ', 'Retenes', 3, 0, 850, '', 0, 0, '52x72x10', '66')," +
                "('742058', ' 6203', 'Cajebolas', 68, 375, 650, 'Cajebola', 0, 10, '17x40x12', 'China')," +
                "('747601', 'Intermitentes', 'Accesorios de Motos', 4, 2600, 3200, 'Ax100', 0, 2, '', 'AX100')," +
                "('754135', 'Retén ', 'Retenes', 3, 0, 700, '', 0, 0, '32x56x10', '81')," +
                "('755384', 'Retén ', 'Retenes', 2, 0, 850, '', 0, 0, '65x95x10 ', '36')," +
                "('761358', 'Retén ', 'Retenes', 3, 0, 750, '', 0, 0, '40x50', '56')," +
                "('763749', 'Manilla Cloche ETZ', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'Manilla ETZ')," +
                "('764873', 'Retén ', 'Retenes', 3, 0, 1000, '', 0, 0, '110x135x10', '87')," +
                "('767194', 'Retén ', 'Retenes', 3, 0, 700, '', 0, 0, '30x45x8', '28')," +
                "('777104', 'Retén ', 'Retenes', 2, 0, 800, '', 0, 0, '45x65x10', '62')," +
                "('786264', 'Retén ', 'Retenes', 3, 0, 600, '', 0, 0, '22x34.5x5.5', '24')," +
                "('789949', 'Retén ', 'Retenes', 3, 0, 700, '', 0, 0, '32x43x8', '47')," +
                "('791548', 'Bomba de freno GN125', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'GN125')," +
                "('793425', 'Retén ', 'Retenes', 4, 0, 1000, '', 0, 0, '110x130x10', '86')," +
                "('797277', 'Retén ', 'Retenes', 3, 0, 850, '', 0, 0, '70x90x10', 'Repisa')," +
                "('798417', 'Intermitentes Criollos Redondos  ', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'ETZ')," +
                "('799983', 'Retén ', 'Retenes', 1, 0, 600, '', 0, 0, '20x30x7 ', '17')," +
                "('800415', 'Cable Cuentamillas GN125', 'Accesorios de Motos', 3, 700, 1000, 'Cuentamillas de GN125', 0, 0, '', 'GN125 ')," +
                "('804665', '6205', 'Cajebolas', 3, 1000, 1800, 'Cajebola', 0, 4, '25x52x15', 'KFB')," +
                "('805783', 'Conmutadores AX100-2', 'Accesorios de Motos', 5, 0, 0, '', 0, 0, '', 'AX100 - 2')," +
                "('810398', 'Retén ', 'Retenes', 4, 0, 650, '', 0, 0, '25x62x8', '63')," +
                "('812400', 'Retén ', 'Retenes', 1, 0, 600, '', 0, 0, '20x35x10', '8')," +
                "('813783', 'Cadena Reforzada 132', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', '132 eslabón ')," +
                "('818342', 'Correa A35', 'Correas', 0, 0, 0, '', 0, 0, '', 'A35')," +
                "('820321', 'Comnumtador Integral Gris', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'Gris')," +
                "('823224', 'Retén ', 'Retenes', 1, 0, 600, '', 0, 0, '22x47x7', '28')," +
                "('829663', 'Retén ', 'Retenes', 4, 0, 700, '', 0, 0, '35x50x8', '33')," +
                "('842640', 'Retén ', 'Retenes', 1, 0, 550, '', 0, 0, '17x30x7', '3')," +
                "('851743', 'Retén ', 'Retenes', 4, 0, 850, '', 0, 0, '70x95x10', 'Repisa')," +
                "('852849', 'Calzo Espro Criollo Suzuki ', 'Accesorios de Motos', 5, 150, 300, '', 0, 0, '', 'Suzuki ')," +
                "('855348', 'Retén ', 'Retenes', 4, 0, 750, '', 0, 0, '45x62x8', '57')," +
                "('857225', 'Cable Criollo Colores', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'Cable colores')," +
                "('864333', 'Cable de cloche de GN125', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'GN125')," +
                "('867201', 'Retén ', 'Retenes', 4, 0, 650, '', 0, 0, '25x48x8', '44')," +
                "('869483', 'Relay de Arranque 4t', 'Accesorios de Motos', 8, 800, 1500, 'Relay', 0, 3, '', 'Relay de Arranque ')," +
                "('876027', 'Retén ', 'Retenes', 1, 0, 550, '', 0, 0, '18x48x10', '41')," +
                "('891855', 'Retén ', 'Retenes', 4, 0, 750, '', 0, 0, '42x64x8', 'Repisa ')," +
                "('891957', 'Retén ', 'Retenes', 1, 0, 500, '', 0, 0, '15x25x6', '4')," +
                "('897161', 'Retén ', 'Retenes', 4, 0, 650, '', 0, 0, '25x40x8', '37')," +
                "('901777', ' 6303', 'Cajebolas', 15, 500, 900, 'Bomba de agua Mosckovich', 0, 5, '17x47x14', 'China')," +
                "('920822', 'Retén ', 'Retenes', 4, 0, 650, 'Piñón de salida del TS - ETZ (5ta)', 0, 0, '25x35x7', '8')," +
                "('921993', 'Reten ', 'Retenes', 4, 250, 400, '', 0, 0, '8x16x7', '3')," +
                "('924753', 'Retén ', 'Retenes', 1, 0, 500, '', 0, 0, '15x30x7', '18')," +
                "('931335', 'Retén ', 'Retenes', 4, 0, 750, '', 0, 0, '39.5x52x10', '37')," +
                "('934060', 'Retén ', 'Retenes', 4, 0, 850, '', 0, 0, '65x90x10', 'Repisa')," +
                "('935674', 'Manifor de AX100', 'Accesorios de Motos', 1, 700, 1200, 'Manifor de AX100', 0, 0, '', 'AX100')," +
                "('938949', '6006', 'Cajebolas', 17, 600, 900, 'Cajebola', 0, 5, '30x55x13', 'China')," +
                "('940190', 'Retén ', 'Retenes', 4, 0, 700, '', 0, 0, '30x42x7', '26')," +
                "('940399', 'Retén ', 'Retenes', 4, 0, 650, '', 0, 0, '27x40x6', '31')," +
                "('950958', 'Retén ', 'Retenes', 1, 0, 600, '', 0, 0, '20x34x6', '36')," +
                "('951644', 'Retén ', 'Retenes', 4, 0, 800, '', 0, 0, '50x70x10', '64')," +
                "('961757', 'Retén ', 'Retenes', 4, 0, 600, '', 0, 0, '24x35x7', '8')," +
                "('962143', 'Retén ', 'Retenes', 1, 0, 500, '', 0, 0, '15x25.5x7', '11')," +
                "('962867', 'Retén ', 'Retenes', 1, 0, 0, '', 0, 0, '18x30x7', '23')," +
                "('966089', 'Cadena Reforzada AX100', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'Cadena AX100')," +
                "('968284', 'Retén ', 'Retenes', 4, 0, 700, '', 0, 0, '42x62x8', '61')," +
                "('977870', 'Retén ', 'Retenes', 3, 0, 1000, '', 0, 0, '80x100', '85')," +
                "('991393', '6204', 'Cajebolas', 108, 700, 1200, 'Cajebola', 0, 10, '20x47x14', 'KFB')," +
                "('997257', 'Retén ', 'Retenes', 4, 0, 750, '', 0, 0, '40x52x7', '72');")




        //ProductsLS
        db.execSQL("INSERT INTO `ProductLS` VALUES" +
                "('109993', 'Retén ', 'Estante Madera_Retenes_Repisa', 1, 0, 1000, '', 0, 0, '120x150x10', 'Repisa')," +
                "('113165', 'Retén ', 'Estante Madera_Retenes_70', 1, 0, 650, '', 0, 0, '25x72x8', '70')," +
                "('113480', '6301', '6300\\n6301_Chinas', 5, 375, 650, 'Centros de moto eléctrica, ax100 trasera', 0, 10, '12x37x12', 'China')," +
                "('117479', 'Retén ', 'Estante Madera_Retenes_46', 1, 0, 650, '', 0, 0, '28x42x7', '46')," +
                "('119425', 'Retén ', 'Estante Madera_Retenes_65', 1, 0, 800, '', 0, 0, '48x73x10', '65')," +
                "('127666', 'Reten', 'Estante Madera_Retenes_3', 1, 250, 400, '', 0, 1, '7x16x7', '3')," +
                "('134647', 'Retén ', 'Estante Madera_Retenes_9', 1, 0, 600, '', 0, 0, '20x37x8', '9')," +
                "('136655', 'Retén ', 'Estante Madera_Retenes_Repisa', 2, 0, 1000, '', 0, 0, '90x120x10', 'Repisa')," +
                "('137589', 'Retén ', 'Estante Madera_Retenes_46', 1, 0, 700, '', 0, 0, '28x47x8', '46')," +
                "('139186', 'Retén ', 'Estante Madera_Retenes_59', 1, 0, 550, '', 0, 0, '17x47x8', '59')," +
                "('142167', 'Retén ', 'Estante Madera_Retenes_Repisa', 1, 0, 850, '', 0, 0, '70x92x10', 'Repisa')," +
                "('142856', 'Retén ', 'Estante Madera_Retenes_12', 1, 0, 500, '', 0, 0, '15x32x7', '12')," +
                "('163531', 'Retén ', 'Estante Madera_Retenes_5', 4, 0, 750, '', 0, 0, '16x24x7', '5')," +
                "('166634', 'Retén ', 'Estante Madera_Retenes_67', 2, 0, 850, '', 0, 0, '53x68x10', '67')," +
                "('187732', 'Retén ', 'Estante Madera_Retenes_Repisa', 1, 0, 850, '', 0, 0, '65x90x12', 'Repisa')," +
                "('188179', 'Retén ', 'Estante Madera_Retenes_59', 1, 0, 0, '', 0, 0, '17x47x10', '59')," +
                "('221862', 'Retén ', '74', 1, 0, 700, '', 0, 0, '35.8x68x12', '74')," +
                "('223454', 'Retén ', 'Estante Madera_Retenes_14', 1, 0, 500, '', 0, 0, '15x35x7', '14')," +
                "('232507', 'Retén ', 'Estante Madera_Retenes_69', 2, 0, 700, '', 0, 0, '30x62x8', '69')," +
                "('240398', 'Retén ', 'Estante Madera_Retenes_Repisa', 1, 0, 900, '', 0, 0, '80x105x10', 'Repisa')," +
                "('243150', 'Retén ', 'Estante Madera_Retenes_52', 2, 0, 650, '', 0, 0, '25x50x12', '52')," +
                "('254143', 'Retén ', 'Estante Madera_Retenes_54', 1, 0, 750, '', 0, 0, '41x56x8', '54')," +
                "('256208', 'Retén ', 'Estante Madera_Retenes_49', 1, 0, 500, '', 0, 0, '16x47x10', '49')," +
                "('263820', 'Retén ', 'Estante Madera_Retenes_6', 1, 0, 500, '', 0, 0, '16x30x7', '6')," +
                "('264282', 'Retén ', '82', 1, 0, 900, '', 0, 0, '75x100x10', '82')," +
                "('271428', 'Retén ', 'Estante Madera_Retenes_20', 1, 0, 400, '', 0, 0, '12x22x7', '20')," +
                "('277412', 'Retén ', 'Estante Madera_Retenes_42', 1, 0, 600, '', 0, 0, '22x35x7', '42')," +
                "('279922', 'Retén ', 'Estante Madera_Retenes_Repisa', 1, 0, 700, '', 0, 0, '42x68x8', 'Repisa ')," +
                "('283311', '6206', '6206_KFB', 3, 600, 100, 'Cajebola', 0, 5, '30x62x16', 'KFB')," +
                "('283765', 'Retén ', 'Estante Madera_Retenes_27', 1, 0, 700, '', 0, 0, '30x41x7', '27')," +
                "('293527', 'Intermitentes ETZ (Criollo)', 'Accesorios  de motos', 0, 0, 0, '', 0, 0, '', '')," +
                "('302570', '6202', '6202\\nChinas', 5, 300, 550, 'Cajebola', 0, 10, '15x35x11', 'China')," +
                "('309022', 'Retén ', 'Estante Madera_Retenes_35', 1, 0, 0, '', 0, 0, '18x32x5.5', '35')," +
                "('316744', 'Retén ', 'Estante Madera_Retenes_39', 1, 0, 700, '', 0, 0, '31x41x7', '39')," +
                "('336061', '6000', '6000\\n6001\\n_Chinas', 5, 250, 450, 'Cajebola', 0, 4, '10x26x8', 'China ')," +
                "('340502', 'A35', 'A', 0, 0, 0, '', 0, 0, 'A35', 'A35')," +
                "('344668', 'Retén ', '80', 1, 0, 850, '', 0, 0, '55x82x10', '80')," +
                "('364624', '6302', '6302_Chinas', 5, 450, 750, 'Rueda trasera suzuki,taeko,GN125', 0, 10, '15x42x13', 'China')," +
                "('375166', 'Retén ', 'Estante Madera_Retenes_38', 1, 0, 700, '', 0, 0, '31x43x10', '38')," +
                "('376697', 'Retén ', 'Estante Madera_Retenes_51', 2, 0, 700, '', 0, 0, '36x52x10', '51')," +
                "('383143', 'Retén ', '75', 1, 0, 850, '', 0, 0, '60x75x8', '75')," +
                "('389316', '6005', '6005\\n6006_Chinas', 5, 520, 850, 'Caña de Suzuki ax100', 0, 19, '25x47x12', 'China')," +
                "('391430', 'Correa', 'Correas_Tipos_A', 0, 0, 0, '', 0, 0, 'A34', 'A34')," +
                "('394853', '6201', '6201_SKF', 5, 500, 850, 'Cajebola', 0, 5, '12x32x10', 'SKF')," +
                "('402558', 'Retén ', 'Estante Madera_Retenes_Repisa', 1, 0, 750, '', 0, 0, '42x68x10', 'Repisa ')," +
                "('405344', 'Retén ', 'Estante Madera_Retenes_21', 1, 0, 450, '', 0, 0, '14.8x30x7', '21')," +
                "('424133', 'Retén ', 'Estante Madera_Retenes_34', 1, 0, 650, '', 0, 0, '25x52x7', '34')," +
                "('427435', 'Retén ', 'Estante Madera_Retenes_Repisa', 1, 0, 850, '', 0, 0, '60x60x10', 'Repisa')," +
                "('436461', 'Correas A34', 'A', 0, 0, 0, '', 0, 0, '', 'A34')," +
                "('438708', 'Retén ', 'Estante Madera_Retenes_9', 1, 0, 600, '', 0, 0, '20x47x10', '9')," +
                "('441538', 'Retén ', 'Estante Madera_Retenes_7', 1, 0, 600, '', 0, 0, '20x32x7', '7')," +
                "('441997', 'Retén ', 'Estante Madera_Retenes_55', 2, 0, 750, '', 0, 0, '40x56x8', '55')," +
                "('445969', 'Retén ', 'Estante Madera_Retenes_25', 1, 0, 700, '', 0, 0, '30x40.5x10.5', '25')," +
                "('450987', 'Retén ', 'Estante Madera_Retenes_Repisa', 1, 0, 1000, '', 0, 0, '100x120x10', 'Repisa')," +
                "('471931', 'Retén ', '77', 1, 0, 850, '', 0, 0, '68x95x10', '77')," +
                "('476508', 'Retén ', 'Estante Madera_Retenes_1', 1, 0, 850, '', 0, 0, '68x86x8', '1')," +
                "('480430', 'Retén ', 'Estante Madera_Retenes_16', 1, 0, 600, '', 0, 0, '20x36x7', '16')," +
                "('487106', 'Retén ', 'Estante Madera_Retenes_46', 3, 0, 700, '', 0, 0, '35x45x8', '48')," +
                "('488529', 'Retén ', 'Estante Madera_Retenes_43', 1, 0, 650, '', 0, 0, '26x52x8', '43')," +
                "('494347', 'Retén ', 'Estante Madera_Retenes_60', 1, 0, 700, '', 0, 0, '38x54x8', '60')," +
                "('500113', 'Retén ', 'Estante Madera_Retenes_3', 1, 0, 400, '', 0, 0, '10x20x7', '3')," +
                "('511469', ' 6304', '6304_SKF', 5, 1400, 1800, 'Cigueñal de ax100', 0, 4, '20x52x15', 'SKF')," +
                "('515591', 'Retén ', 'Estante Madera_Retenes_32', 1, 0, 700, '', 0, 0, '30x52x7', '32')," +
                "('519840', 'Retén ', '73', 1, 0, 750, '', 0, 0, '42x58x9', '73')," +
                "('523372', 'Retén ', 'Estante Madera_Retenes_45', 1, 0, 600, '', 0, 0, '20x40x8', '45')," +
                "('525699', 'Retén ', 'Estante Madera_Retenes_10', 1, 0, 600, '', 0, 0, '22x36.5x6', '10')," +
                "('550017', '6201', '6201_Chinas', 5, 300, 550, 'Centro delantero de suzuki ax100 (Banda)', 0, 10, '12x32x10', 'China')," +
                "('556251', 'Retén ', 'Estante Madera_Retenes_8', 1, 0, 550, '', 0, 0, '19.5x35x8', '8')," +
                "('558525', 'Retén ', 'Estante Madera_Retenes_50', 3, 0, 700, '', 0, 0, '30x50x7', '50')," +
                "('571812', 'Retén ', 'Estante Madera_Retenes_13', 1, 0, 550, '', 0, 0, '18.9x30x7', '13')," +
                "('587038', 'Retén ', 'Estante Madera_Retenes_56', 1, 0, 750, '', 0, 0, '44x60x7', '56')," +
                "('591306', 'Retén ', 'Estante Madera_Retenes_30', 1, 0, 650, '', 0, 0, '27x39x7', '30')," +
                "('595561', 'Retén ', '71', 1, 0, 800, '', 0, 0, '50x68x7', '71')," +
                "('600172', 'Retén ', 'Estante Madera_Retenes_61', 1, 0, 700, '', 0, 0, '42x62x10', '61')," +
                "('615908', 'Foco trasero (Criollo)', 'Accesorios  de motos', 0, 0, 0, '', 0, 0, '', 'Largo')," +
                "('629865', 'Retén ', 'Estante Madera_Retenes_29', 1, 0, 700, '', 0, 0, '32x44x10.5', '29')," +
                "('636113', 'Retén ', 'Estante Madera_Retenes_15', 1, 0, 600, '', 0, 0, '19x30x7', '15')," +
                "('638751', 'Retén ', 'Estante Madera_Retenes_40', 1, 0, 550, '', 0, 0, '18x47x7', '40')," +
                "('639810', 'Retén ', 'Estante Madera_Retenes_1', 1, 0, 550, '', 0, 0, '17x32x8', '1')," +
                "('643749', 'Retén ', '76', 1, 0, 800, '', 0, 0, '50x65x10 ', '76')," +
                "('644435', 'Intermitentes ETZ (Criollo)', 'Accesorios  de motos', 0, 0, 0, '', 0, 0, '', '')," +
                "('656413', 'Retén ', 'Estante Madera_Retenes_2', 2, 0, 700, '', 0, 0, '35x47x7', '2')," +
                "('665009', 'Retén ', '79', 1, 0, 850, '', 0, 0, '52x65x8', '79')," +
                "('665466', 'Retén ', 'Estante Madera_Retenes_68', 1, 0, 750, '', 0, 0, '42x75x10', '68')," +
                "('689119', 'Retén ', 'Estante Madera_Retenes_35', 1, 0, 850, '', 0, 0, '60x85x10 ', '35')," +
                "('699249', 'Retén ', 'Estante Madera_Retenes_Repisa', 1, 0, 850, '', 0, 0, '60x90x10', 'Repisa')," +
                "('699812', 'Retén ', 'Estante Madera_Retenes_Repisa', 1, 0, 850, '', 0, 0, '52x68x8', 'Repisa')," +
                "('701437', 'Retén ', 'Estante Madera_Retenes_19', 1, 0, 0, '', 0, 0, '18x37x7', '19')," +
                "('705749', 'Retén ', 'Estante Madera_Retenes_58', 1, 0, 700, '', 0, 0, '33.4x63x10', '58')," +
                "('711177', '6205', '6205_Chinas', 2, 700, 1000, 'Cigueñal Suzuki ', 0, 10, '25x52x15', 'China')," +
                "('719908', 'Foco trasero ETZ (Criollo)', 'Accesorios  de motos', 0, 0, 0, '', 0, 0, '', 'Corto')," +
                "('723126', 'Repisa', 'Estante Madera_Retenes_Repisa', 1, 0, 1000, '', 0, 0, '95x127x14', 'Repisa')," +
                "('728478', 'Retén ', 'Estante Madera_Retenes_41', 1, 0, 650, '', 0, 0, '28x40x7', '41')," +
                "('728534', '6004', '6003\\p004_Chinas', 9, 450, 700, 'Cajebola', 0, 5, '20x42x12', 'China')," +
                "('733892', 'Retén ', 'Estante Madera_Retenes_66', 1, 0, 850, '', 0, 0, '52x72x10', '66')," +
                "('742058', ' 6203', '6203_Chinas', 5, 375, 650, 'Cajebola', 0, 10, '17x40x12', 'China')," +
                "('754135', 'Retén ', '81', 2, 0, 700, '', 0, 0, '32x56x10', '81')," +
                "('755384', 'Retén ', 'Estante Madera_Retenes_36', 1, 0, 850, '', 0, 0, '65x95x10 ', '36')," +
                "('761358', 'Retén ', 'Estante Madera_Retenes_56', 1, 0, 750, '', 0, 0, '40x50', '56')," +
                "('764873', 'Retén ', '87', 2, 0, 1000, '', 0, 0, '110x135x10', '87')," +
                "('767194', 'Retén ', 'Estante Madera_Retenes_28', 1, 0, 700, '', 0, 0, '30x45x8', '28')," +
                "('777104', 'Retén ', 'Estante Madera_Retenes_62', 1, 0, 800, '', 0, 0, '45x65x10', '62')," +
                "('786264', 'Retén ', 'Estante Madera_Retenes_24', 2, 0, 600, '', 0, 0, '22x34.5x5.5', '24')," +
                "('789949', 'Retén ', 'Estante Madera_Retenes_47', 1, 0, 700, '', 0, 0, '32x43x8', '47')," +
                "('793425', 'Retén ', '86', 1, 0, 1000, '', 0, 0, '110x130x10', '86')," +
                "('797277', 'Retén ', 'Estante Madera_Retenes_Repisa', 2, 0, 850, '', 0, 0, '70x90x10', 'Repisa')," +
                "('799983', 'Retén ', 'Estante Madera_Retenes_17', 1, 0, 600, '', 0, 0, '20x30x7 ', '17')," +
                "('804665', '6205', '6205_KFB', 12, 1000, 1800, 'Cajebola', 0, 4, '', 'KFB')," +
                "('810398', 'Retén ', 'Estante Madera_Retenes_63', 1, 0, 650, '', 0, 0, '25x62x8', '63')," +
                "('812400', 'Retén ', 'Estante Madera_Retenes_8', 1, 0, 600, '', 0, 0, '20x35x10', '8')," +
                "('823224', 'Retén ', 'Estante Madera_Retenes_28', 1, 0, 600, '', 0, 0, '22x47x7', '28')," +
                "('829663', 'Retén ', 'Estante Madera_Retenes_33', 1, 0, 700, '', 0, 0, '35x50x8', '33')," +
                "('839821', 'Bomba de Freno ', 'Accesorios  de motos', 0, 0, 0, '', 0, 0, '', 'GN125')," +
                "('842640', 'Retén ', 'Estante Madera_Retenes_3', 1, 0, 550, '', 0, 0, '17x30x7', '3')," +
                "('851743', 'Retén ', 'Estante Madera_Retenes_Repisa', 1, 0, 850, '', 0, 0, '70x95x10', 'Repisa')," +
                "('855348', 'Retén ', 'Estante Madera_Retenes_57', 1, 0, 750, '', 0, 0, '45x62x8', '57')," +
                "('867201', 'Retén ', 'Estante Madera_Retenes_44', 1, 0, 650, '', 0, 0, '25x48x8', '44')," +
                "('876027', 'Retén ', 'Estante Madera_Retenes_41', 1, 0, 550, '', 0, 0, '18x48x10', '41')," +
                "('891855', 'Retén ', 'Estante Madera_Retenes_Repisa', 1, 0, 750, '', 0, 0, '42x64x8', 'Repisa ')," +
                "('891957', 'Retén ', 'Estante Madera_Retenes_4', 1, 0, 500, '', 0, 0, '15x25x6', '4')," +
                "('897161', 'Retén ', 'Estante Madera_Retenes_37', 1, 0, 650, '', 0, 0, '25x40x8', '37')," +
                "('901777', ' 6303', '6303_Chinas', 5, 500, 900, 'Bomba de agua Mosckovich', 0, 5, '17x47x14', 'China')," +
                "('920822', 'Retén ', 'Estante Madera_Retenes_8', 1, 0, 650, 'Piñón de salida del TS - ETZ (5ta)', 0, 0, '25x35x7', '8')," +
                "('921993', 'Reten ', 'Estante Madera_Retenes_3', 1, 250, 400, '', 0, 0, '8x16x7', '3')," +
                "('924753', 'Retén ', 'Estante Madera_Retenes_18', 1, 0, 500, '', 0, 0, '15x30x7', '18')," +
                "('931335', 'Retén ', 'Estante Madera_Retenes_37', 1, 0, 750, '', 0, 0, '39.5x52x10', '37')," +
                "('934060', 'Retén ', 'Repisa', 1, 0, 850, '', 0, 0, '65x90x10', 'Repisa')," +
                "('940190', 'Retén ', 'Estante Madera_Retenes_26', 1, 0, 700, '', 0, 0, '30x42x7', '26')," +
                "('940399', 'Retén ', 'Estante Madera_Retenes_31', 1, 0, 650, '', 0, 0, '27x40x6', '31')," +
                "('948276', 'Correa A36', 'A', 0, 0, 0, '', 0, 0, 'A36', 'A36')," +
                "('950958', 'Retén ', 'Estante Madera_Retenes_36', 1, 0, 600, '', 0, 0, '20x34x6', '36')," +
                "('951644', 'Retén ', 'Estante Madera_Retenes_64', 1, 0, 800, '', 0, 0, '50x70x10', '64')," +
                "('961757', 'Retén ', 'Estante Madera_Retenes_8', 1, 0, 600, '', 0, 0, '24x35x7', '8')," +
                "('962143', 'Retén ', 'Estante Madera_Retenes_11', 1, 0, 500, '', 0, 0, '15x25.5x7', '11')," +
                "('962867', 'Retén ', 'Estante Madera_Retenes_23', 1, 0, 0, '', 0, 0, '18x30x7', '23')," +
                "('964405', 'A35', 'Correas_Tipos_A', 0, 0, 0, '', 0, 0, 'A35', 'Correa')," +
                "('968284', 'Retén ', 'Estante Madera_Retenes_61', 1, 0, 700, '', 0, 0, '42x62x8', '61')," +
                "('977870', 'Retén ', '85', 2, 0, 1000, '', 0, 0, '80x100', '85')," +
                "('991393', '6204', '6204_KFB', 5, 700, 1200, 'Cajebola', 0, 10, '20x47x14', 'KFB')," +
                "('997257', 'Retén ', '72', 1, 0, 750, '', 0, 0, '40x52x7', '72');")


    }
}