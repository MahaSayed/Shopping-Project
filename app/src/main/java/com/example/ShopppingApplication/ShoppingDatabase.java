package com.example.ShopppingApplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ShoppingDatabase extends SQLiteOpenHelper {

    private static String databaseName = "onlineShopping";  //esm el DB
    SQLiteDatabase onlineShopping;                        //Object mn SQLite

    public ShoppingDatabase(Context context) {
        super(context, databaseName, null, 2);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table customers(custID integer primary key autoincrement,"+
                "custname text not null, username text not null, password text not null,"+
                "gender text not null , birthdate text not null , job text not null)");

        db.execSQL("create table orders(ordID integer primary key autoincrement,"+
                "orddate text not null, cust_ID integer not null, address text not null,"+
                "FOREIGN KEY(cust_ID) REFERENCES customers(custID))");

        db.execSQL("create table categories(catID integer primary key autoincrement,"+
                "catname text not null)");

        db.execSQL("create table products(prodID integer primary key autoincrement,"+
                "prodname text not null, price text not null, quantity integer ,"+
                "cat_ID integer not null, FOREIGN KEY(cat_ID) REFERENCES categories(catID))");

        db.execSQL("create table order_details (ord_ID integer not null,"+
                "prod_ID integer not null, quantity integer not null, PRIMARY KEY (ord_ID,prod_ID),"+
                "FOREIGN KEY(ord_ID) REFERENCES orders(ordID), FOREIGN KEY(prod_ID) REFERENCES products(prodID))");

        ContentValues categoriesRaw = new ContentValues();
        ContentValues productRaw = new ContentValues();
        categoriesRaw.put("catname" , "Mobiles");
        db.insert("categories" ,null , categoriesRaw);

        productRaw.put("prodname" , "HUAWEI");
        productRaw.put("price" , "10000");
        productRaw.put("quantity" ,6);
        productRaw.put("cat_ID" , 1);
        db.insert("products" , null , productRaw);

        productRaw.put("prodname" , "SAMSUNG");
        productRaw.put("price" , "15000");
        productRaw.put("quantity" , 100);
        productRaw.put("cat_ID" , 1);
        db.insert("products" , null , productRaw);

        productRaw.put("prodname" , "SONY");
        productRaw.put("price" , "6000");
        productRaw.put("quantity" , 10);
        productRaw.put("cat_ID" , 1);
        db.insert("products" , null , productRaw);

        productRaw.put("prodname" , "IPHONE");
        productRaw.put("price" , "24000");
        productRaw.put("quantity" , 20);
        productRaw.put("cat_ID" , 1);
        db.insert("products" , null , productRaw);

        productRaw.put("prodname" , "NOKIA");
        productRaw.put("price" , "2500");
        productRaw.put("quantity" , 40);
        productRaw.put("cat_ID" , 1);
        db.insert("products" , null , productRaw);

        categoriesRaw.put("catname" , "Laptops");
        db.insert("categories" ,null , categoriesRaw);

        productRaw.put("prodname" , "HP");
        productRaw.put("price" , "10000");
        productRaw.put("quantity" , 60);
        productRaw.put("cat_ID" , 2);
        db.insert("products" , null , productRaw);

        productRaw.put("prodname" , "LENOVO");
        productRaw.put("price" , "11000");
        productRaw.put("quantity" , 52);
        productRaw.put("cat_ID" , 2);
        db.insert("products" , null , productRaw);

        productRaw.put("prodname" , "DELL");
        productRaw.put("price" , "15000");
        productRaw.put("quantity" , 75);
        productRaw.put("cat_ID" , 2);
        db.insert("products" , null , productRaw);

        productRaw.put("prodname" , "TOSHIBA");
        productRaw.put("price" , "6555");
        productRaw.put("quantity" , 50);
        productRaw.put("cat_ID" , 2);
        db.insert("products" , null , productRaw);

        productRaw.put("prodname" , "APPLE");
        productRaw.put("price" , "50000");
        productRaw.put("quantity" , 50);
        productRaw.put("cat_ID" , 2);
        db.insert("products" , null , productRaw);


        db.execSQL("create table cart(pro_ID integer primary key ,quantity integer not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists customers");
        db.execSQL("drop table if exists orders");
        db.execSQL("drop table if exists categories");
        db.execSQL("drop table if exists products");
        db.execSQL("drop table if exists order_details");
        onCreate(db);
    }

    public void CreateNewCustomer(String name,String username,String pass,String gender,String birthdate,String job)
    {
        onlineShopping=getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("custname",name);
        row.put("username" , username);
        row.put("password" , pass);
        row.put("gender" , gender);
        row.put("birthdate" , birthdate);
        row.put("job" , job);
        onlineShopping.insert("customers",null,row);
        onlineShopping.close();
    }
    public Cursor getAllCustomers()
    {
        onlineShopping = getReadableDatabase();
        String[] rowDetails = {"username"};
        Cursor cur = onlineShopping.query("customers" , rowDetails,null,null,null,null,null);
        if (cur != null)
            cur.moveToFirst();
        onlineShopping.close();
        return cur;
    }

    public Cursor forgetPassword(String username , String birthdate)
    {
        onlineShopping = getReadableDatabase();
        Cursor cursor = onlineShopping.rawQuery("select * from customers where username like '" +username+ "' AND birthdate like '" +birthdate+ "' ", null);
        if(cursor != null)
            cursor.moveToFirst();
        onlineShopping.close();
        return  cursor;
    }
    public void UpdatePassword (String username , String newPassword)
    {
        onlineShopping = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("password" , newPassword);
        onlineShopping.update("customers" , row , "username like ?" , new String[] {username});
        onlineShopping.close();
    }
    public Cursor checkLogIn(String username , String password)
    {
        onlineShopping = getReadableDatabase();
        Cursor cursor = onlineShopping.rawQuery("select * from customers where username like '" +username+ "' AND password like '" +password+ "' ", null);
        if(cursor != null)
            cursor.moveToFirst();
        onlineShopping.close();
        return  cursor;
    }
    public Cursor getCategories()
    {
        onlineShopping = getReadableDatabase();
        Cursor cur = onlineShopping.rawQuery("select * from categories" ,null);
        if (cur != null)
            cur.moveToFirst();
        onlineShopping.close();
        return cur;
    }
    public String getCategoyName(Integer catID)
    {
        onlineShopping = getReadableDatabase();
        Cursor cur = onlineShopping.rawQuery("select catname from categories where catID like'"+catID+"'" ,null);
        String name  = null;
        if(cur != null) {
            cur.moveToFirst();
            name = cur.getString(0);
        }
        onlineShopping.close();
        return name;
    }
    public Cursor getProducts(Integer catID)
    {
        onlineShopping = getReadableDatabase();
        Cursor cursor = onlineShopping.rawQuery("select * from products where cat_ID like '" +catID+ "' ", null);
        if(cursor != null)
            cursor.moveToFirst();

        onlineShopping.close();
        return  cursor;
    }
    public Cursor getProductInfo(Integer productID) {

        onlineShopping = getReadableDatabase();
        Cursor cursor = onlineShopping.rawQuery("select * from products where prodID like '" +productID+ "' ", null);
        if(cursor != null)
            cursor.moveToFirst();
        onlineShopping.close();
        return  cursor;
    }
    public Cursor SearchProduct(String text)
    {
        onlineShopping = getReadableDatabase();
        Cursor cursor = onlineShopping.rawQuery("select * from products where prodname like '" +text+ "' ", null);
        if(cursor != null)
            cursor.moveToFirst();
        onlineShopping.close();
        return  cursor;
    }
    public String getProductPrice(Integer id)
    {
        onlineShopping = getReadableDatabase();
        Cursor cursor = onlineShopping.rawQuery("select price from products where prodID like '" +id+ "' ", null);
        String price  = null;
        if(cursor != null) {
            cursor.moveToFirst();
            price = cursor.getString(0);
        }
        onlineShopping.close();
        return price;
    }
    public Integer getProductQuantity(Integer id)
    {
        onlineShopping = getReadableDatabase();
        Cursor cursor = onlineShopping.rawQuery("select quantity from products where prodID like '" +id+ "' ", null);
        Integer quantity  = null;
        if(cursor != null) {
            cursor.moveToFirst();
            quantity = cursor.getInt(0);
        }
        onlineShopping.close();
        return quantity;
    }
    public void CreateNewOrder(Integer custID,String date,String address)
    {
        onlineShopping = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("orddate",date);
        row.put("cust_ID" , custID);
        row.put("address" , address);

        onlineShopping.insert("orders",null,row);
        onlineShopping.close();
    }
    public Integer getLastOrderID()
    {
        onlineShopping = getReadableDatabase();
        Cursor cursor = onlineShopping.rawQuery("select * from orders ", null);
        Integer countID = cursor.getCount();
        onlineShopping.close();
        return countID;
    }
    public void OrderDetails(Integer ordID,Integer prodID,Integer quantity)
    {
        onlineShopping = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("ord_ID",ordID);
        row.put("prod_ID" , prodID);
        row.put("quantity" , quantity);

        onlineShopping.insert("order_details",null,row);
        onlineShopping.close();
    }
    public void addtoCart(Integer id,Integer q)
    {
        onlineShopping = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("pro_ID",id);
        row.put("quantity" , q);
        onlineShopping.insert("cart",null,row);
        onlineShopping.close();
    }
    public Cursor fetchCart()
    {
        onlineShopping = getReadableDatabase();
        String[] rowDetails = {"pro_ID","quantity"};
        Cursor cur = onlineShopping.query("cart" , rowDetails,null,null,null,null,null);
        if (cur != null)
            cur.moveToFirst();
        onlineShopping.close();
        return cur;
    }
    public void editQuantity (Integer id , Integer newQuantity)
    {
        onlineShopping = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("quantity" , newQuantity);
        onlineShopping.update("cart" , row , "pro_ID like '"+id+"' " , null);
        onlineShopping.close();
    }
    public void deleteItem(Integer id)
    {
        onlineShopping = getWritableDatabase();
        onlineShopping.delete("cart","pro_ID='"+id+"'" , null);
        onlineShopping.close();
    }
    public Integer getQuantity(Integer id)
    {
        onlineShopping = getReadableDatabase();
        Cursor cur = onlineShopping.rawQuery("select quantity from cart where pro_ID like '"+id+"' " , null);
        Integer qnty = null;
        if (cur != null) {
            cur.moveToFirst();
            qnty = cur.getInt(0);
        }
        onlineShopping.close();
        return qnty;
    }
    public void deleteCart()
    {
        onlineShopping = getWritableDatabase();
        onlineShopping.delete("cart",null , null);
        onlineShopping.close();
    }
    public void editProduct (Integer id , Integer newQuantity)
    {
        onlineShopping = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("quantity" , newQuantity);
        onlineShopping.update("products" , row , "prodID like '"+id+"' " , null);
        onlineShopping.close();
    }
}
