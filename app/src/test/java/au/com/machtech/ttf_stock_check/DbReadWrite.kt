package au.com.machtech.ttf_stock_check

import androidx.test.core.app.ApplicationProvider
import au.com.machtech.ttf_stock_check.database.ProductsDb
import au.com.machtech.ttf_stock_check.database.ProductsDbHelper
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(manifest=Config.NONE)
class DbReadWrite {
    private lateinit var testDbHelper: ProductsDbHelper

    @Before
    fun setup() {
        testDbHelper = ProductsDbHelper(ApplicationProvider.getApplicationContext ())
        testDbHelper.clearDbAndRecreate()
        testDbHelper.close()
    }

    @Test
    @Throws(Exception::class)
    fun testDbInsert() {

        // Give tests data
        val testCode = "testing code"
        val testDesc = "testing description"
        val testCount = "testing count"
        val testDb = ProductsDb(
            itemCode = testCode,
            itemDescription = testDesc,
            itemCount = testCount)
        //insert the product
        testDbHelper.insertProduct(testDb)
        // check if it added
        assertEquals(testDbHelper.getAllProducts().toString(), "[ProductsDb(itemCode=testing code, itemDescription=testing description, itemCount=testing count)]")
        testDbHelper.close()
    }
    @Test
    @Throws(Exception::class)
    fun testDbUpdate(){
        testDbInsert()
        val testCode = "testing code"
        val testDesc = "testing description change"
        val testCount = "testing count change"
        val testDb = ProductsDb(
            itemCode = testCode,
            itemDescription = testDesc,
            itemCount = testCount)
        testDbHelper.updateProduct(testDb)
        // check if it updated
        assertEquals(testDbHelper.getAllProducts().toString(), "[ProductsDb(itemCode=testing code, itemDescription=testing description change, itemCount=testing count change)]")
        testDbHelper.close()
    }
    @After
    fun tearDown() {
        testDbHelper.clearDb()
        testDbHelper.close()
    }
}
