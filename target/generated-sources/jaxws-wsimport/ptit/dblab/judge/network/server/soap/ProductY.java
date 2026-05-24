
package ptit.dblab.judge.network.server.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for productY complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="productY">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="discount" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         <element name="finalPrice" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         <element name="price" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         <element name="taxRate" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "productY", propOrder = {
    "discount",
    "finalPrice",
    "price",
    "taxRate"
})
public class ProductY {

    protected float discount;
    protected float finalPrice;
    protected float price;
    protected float taxRate;

    /**
     * Gets the value of the discount property.
     * 
     */
    public float getDiscount() {
        return discount;
    }

    /**
     * Sets the value of the discount property.
     * 
     */
    public void setDiscount(float value) {
        this.discount = value;
    }

    /**
     * Gets the value of the finalPrice property.
     * 
     */
    public float getFinalPrice() {
        return finalPrice;
    }

    /**
     * Sets the value of the finalPrice property.
     * 
     */
    public void setFinalPrice(float value) {
        this.finalPrice = value;
    }

    /**
     * Gets the value of the price property.
     * 
     */
    public float getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     */
    public void setPrice(float value) {
        this.price = value;
    }

    /**
     * Gets the value of the taxRate property.
     * 
     */
    public float getTaxRate() {
        return taxRate;
    }

    /**
     * Sets the value of the taxRate property.
     * 
     */
    public void setTaxRate(float value) {
        this.taxRate = value;
    }

}
