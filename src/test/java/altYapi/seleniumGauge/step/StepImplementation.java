package altYapi.seleniumGauge.step;

import altYapi.seleniumGauge.driver.Driver;
import altYapi.seleniumGauge.methods.Methods;
import altYapi.seleniumGauge.methods.MethodsUtil;
import altYapi.seleniumGauge.utils.ReadProperties;
import com.github.javafaker.Faker;
import com.thoughtworks.gauge.Step;
import org.apache.bcel.generic.Select;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.Color;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.Log4jLoggerAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static altYapi.seleniumGauge.driver.Driver.driver;
import static org.junit.jupiter.api.Assertions.*;

public class StepImplementation {

    private static final Log4jLoggerAdapter logger = (Log4jLoggerAdapter) LoggerFactory.getLogger(StepImplementation.class);
    Methods methods;
    MethodsUtil methodsUtil;
    public static ResourceBundle ConfigurationProp = ReadProperties.readProp("Configuration.properties");

    public StepImplementation() {

        methods = new Methods();
        methodsUtil = new MethodsUtil();
    }

    @Step("<key> elementine tıkla")
    public void clickElement(String key) {

        methods.clickElement(methods.getBy(key));
    }

    @Step("<key> elementine çift tıkla")
    public void doubleClickElementWithAction(String key) {

        methods.doubleClickElementWithAction(methods.getBy(key), true);
    }

    @Step("<key> elementine tıkla <notClickByCoordinate> with staleElement")
    public void clickElementForStaleElement(String key, boolean notClickByCoordinate) {

        methods.clickElementForStaleElement(methods.getBy(key), notClickByCoordinate);
    }

    @Step("<key> elementine js ile tıkla")
    public void clickElementJs(String key) {

        methods.clickElementJs(methods.getBy(key));
    }

    @Step("<key> elementine koordinat <x> x ve <y> y ile tıkla")
    public void clickByWebElementCoordinate(String key, int x, int y) {

        methods.clickByWebElementCoordinate(methods.getBy(key), x, y);
    }

    @Step("<key> elementine js ile tıkla <notClickByCoordinate>")
    public void clickElementJs(String key, boolean notClickByCoordinate) {

        methods.clickElementJs(methods.getBy(key), notClickByCoordinate);
    }

    @Step("<key> elementine <text> değerini yaz")
    public void sendKeysElement(String key, String text) {

        text = text.endsWith("KeyValue") ? Driver.TestMap.get(text).toString() : text;
        methods.sendKeys(methods.getBy(key), text);
    }
    @Step("<key> elementine <text> değerini yaz ve entere bas")
    public void sendKeysElementAndEnter(String key, String text) {
        text = text.endsWith("KeyValue") ?
                Driver.TestMap.get(text).toString() : text;
        methods.sendKeys(methods.getBy(key), text);
        waitBySeconds(1);
        methods.sendKeysWithKeys(methods.getBy(key),"ENTER");
    }
    @Step("<key> elementi görünürse, <element> elementine <text> değerini yaz ve entere bas")
    public void sendKeysElementAndEnterIfIsVisible(String key, String element, String text) {
        methods.enterAndSendElementIsVisible(key, element, text);
    }

    @Step("<key> elementi görünürse, <element> elementine <text> değerini yaz ve <clics> elementine tıkla")
    public void sendTextAndClickElementIfVisibleStep(String key, String element, String text, String clicks){
        methods.sendTextAndClickElementIfVisible(key,element,text,clicks);
    }

    @Step("<key> elementine fake <email> mailini yaz")
    public void fakeEmailAdresses(String key, String value) {
        Faker faker = new Faker();
        String firstname = faker.name().firstName();
        String lastname = faker.name().lastName();
        String email="flo.testinium" + firstname.toLowerCase() + methodsUtil.getRandomInt(1,300) + "@gmail.com";
        email = email.endsWith("KeyValue") ? Driver.TestMap.get(email).toString() : email;
        methods.sendKeys(methods.getBy(key), email);
    }
    @Step("<key> elementine fake <isim> değerini yaz")
    public void fakeIsim(String key, String value) {
        Faker faker = new Faker();
        String firstname = faker.name().firstName();
        String isim=firstname.toLowerCase();
        isim = isim.endsWith("KeyValue") ? Driver.TestMap.get(isim).toString() : isim;
        methods.sendKeys(methods.getBy(key), isim);
    }
    @Step("<key> elementine <fileUpload> dosya yolunu yaz")
    public void sendKeysFileUpload(String key, String fileUpload) {

        methods.sendKeys(methods.getBy(key),System.getProperty("user.dir") + fileUpload);
    }

    @Step("<key> elementine js ile <text> değerini yaz")
    public void sendKeysElementWithJsAndBackSpace(String key, String text) {

        methods.waitBySeconds(1);
        methods.jsExecuteScript("arguments[0].value = \"" + text + "\";"
                ,false, methods.findElement(methods.getBy(key)));
        methods.waitByMilliSeconds(500);
        methods.sendKeysWithKeys(methods.getBy(key),"BACK_SPACE");
        methods.waitByMilliSeconds(500);
        methods.sendKeys(methods.getBy(key),text.substring(text.length()-1));
    }

    @Step("<key> elementine <text> sayı değerini yaz")
    public void sendKeysElementWithNumpad(String key, String text) {

        methods.sendKeysWithNumpad(methods.getBy(key), text);
    }

    @Step("<url> adresine git")
    public void navigateTo(String url) {

        url = url.endsWith("KeyValue") ? Driver.TestMap.get(url).toString() : url;
        url = methodsUtil.setValueWithMap(url);
        methods.navigateTo(url);
    }



    @Step("Şu anki url <url> ile aynı mı")
    public void doesUrlEqual(String url) {

        url = url.endsWith("KeyValue") ? Driver.TestMap.get(url).toString() : url;
        url = methodsUtil.setValueWithMap(url);
        assertTrue(methods.doesUrl(url, 80, "equal"),"Beklenen url, sayfa url ine eşit değil");
    }

    @Step("Şu anki url <url> içeriyor mu")
    public void doesUrlContain(String url) {

        url = url.endsWith("KeyValue") ? Driver.TestMap.get(url).toString() : url;
        url = methodsUtil.setValueWithMap(url);
        assertTrue(methods.doesUrl(url, 80, "contain"),"Beklenen url, sayfa url ine eşit değil");
    }


    @Step("Şu anki url <url> ile başlıyor mu")
    public void doesUrlStartWith(String url) {

        url = url.endsWith("KeyValue") ? Driver.TestMap.get(url).toString() : url;
        url = methodsUtil.setValueWithMap(url);
        assertTrue(methods.doesUrl(url, 80, "startWith"),"Beklenen url, sayfa url ine eşit değil");
    }

    @Step("Şu anki url <url> ile sonlanıyor mu")
    public void doesUrlEndWith(String url) {

        url = url.endsWith("KeyValue") ? Driver.TestMap.get(url).toString() : url;
        url = methodsUtil.setValueWithMap(url);
        assertTrue(methods.doesUrl(url, 80, "endWith"),"Beklenen url, sayfa url ine eşit değil");
    }

    @Step("Şu anki url <url> ile farklı mı")
    public void checkUrlDifferent(String url) {

        url = url.endsWith("KeyValue") ? Driver.TestMap.get(url).toString() : url;
        url = methodsUtil.setValueWithMap(url);
        assertTrue(methods.doesUrl(url, 80, "notEqual"),url + " sayfasından başka bir sayfaya geçiş sağlanamadı.");
    }

    @Step("<key> elementinin değerini temizle")
    public void clearElement(String key) {

        methods.clearElement(methods.getBy(key));
    }

    @Step("<key> elementinin değerini back space kullanarak temizle <value>")
    public void clearElementWithBackSpace(String key, String value) {

        methods.clearElementWithBackSpace(methods.getBy(key), value);
    }

    @Step("<seconds> saniye bekle")
    public void waitBySeconds(long seconds) {

        methods.waitBySeconds(seconds);
    }

    @Step("<milliseconds> milisaniye bekle")
    public void waitByMilliSeconds(long milliseconds) {

        methods.waitByMilliSeconds(milliseconds);
    }

    @Step("<key> elementinin görünür olması kontrol edilir")
    public void checkElementVisible(String key) {

        checkElementVisible(key,60);
    }

    @Step("<key> elementinin var olduğu kontrol edilir")
    public void checkElementExist(String key) {

        assertTrue(methods.doesElementExist(methods.getBy(key),80),"");
    }

    @Step("<key> elementinin var olmadığı kontrol edilir")
    public void checkElementNotExist(String key) {

        assertTrue(methods.doesElementNotExist(methods.getBy(key),80),"");
    }

    @Step("<key> elementinin görünür olmadığı kontrol edilir")
    public void checkElementInVisible(String key) {

        checkElementInVisible(key,30);
    }

    @Step("<key> elementinin konumunu aldığı kontrol edilir")
    public void checkElementLocated(String key) {

        checkElementLocated(key,30);
    }

    @Step("<key> elementine dinamik kredi kartı numarası yaz")
    public void paymentWithCreditCard(String key) {
        String text;
        text = ConfigurationProp.getString("creditCard").endsWith("KeyValue") ? Driver.TestMap.get(ConfigurationProp.getString("creditCard")).toString() : ConfigurationProp.getString("creditCard");
        methods.sendKeys(methods.getBy(key), text);
    }

    @Step("<key> elementinin konumunu aldığı kontrol edilir <timeout>")
    public void checkElementLocated(String key, long timeout) {

        methods.checkElementLocated(methods.getBy(key), timeout);
    }

    @Step("<key> elementinin görünür olması kontrol edilir <timeout>")
    public void checkElementVisible(String key, long timeout) {

        methods.checkElementVisible(methods.getBy(key), timeout);
    }

    @Step("<key> elementinin görünür olmadığı kontrol edilir <timeout>")
    public void checkElementInVisible(String key, long timeout) {

        methods.checkElementInVisible(methods.getBy(key), timeout);
    }

    @Step("<key> elementinin tıklanabilir olması kontrol edilir")
    public void checkElementClickable(String key) {

        checkElementClickable(key,30);
    }

    @Step("<key> elementinin tıklanabilir olması kontrol edilir <timeout>")
    public void checkElementClickable(String key, long timeout) {

        methods.checkElementClickable(methods.getBy(key), timeout);
    }

    @Step("<key> elementinin text değerini <mapKey> keyinde tut <trim>")
    public void getElementTextAndSave(String key, String mapKey, boolean trim){

        String text = methods.getText(methods.getBy(key));
        text = trim ? text.trim() : text;
        logger.info(text);
        Driver.TestMap.put(mapKey, text);
    }

    @Step("<key> elementinin <attribute> attribute değerini <mapKey> keyinde tut <trim>")
    public void getElementAttributeAndSave(String key, String attribute, String mapKey, boolean trim){

        String value = methods.getAttribute(methods.getBy(key), attribute);
        value = trim ? value.trim() : value;
        logger.info(value);
        Driver.TestMap.put(mapKey, value);
    }
    @Step({"<key> indirimli fiyatı toplam <expectedText> fiyatına <element> kargo fiyat ve adet <deger> adet dahil eşit mi",
            "get text <key> element and control <expectedText> with cargo price <element>, <deger>"})
    public void controlElementTextsWithKargoPrice(String key, String value, String element, String deger) {
        methods.controlPrice(key,value,element,deger);
    }

    public static String clearTurkishChars(String str) {
        String ret = str;
        char[] turkishChars = new char[]{0x131, 0x130, 0xFC, 0xDC, 0xF6, 0xD6, 0x15F, 0x15E, 0xE7, 0xC7, 0x11F, 0x11E};
        char[] englishChars = new char[]{'i', 'I', 'u', 'U', 'o', 'O', 's', 'S', 'c', 'C', 'g', 'G'};
        for (int i = 0; i < turkishChars.length; i++) {
            ret = ret.replaceAll(new String(new char[]{turkishChars[i]}), new String(new char[]{englishChars[i]}));
        }
        return ret;
    }


    public static float round(float d, int decimalPlace) {
        return BigDecimal.valueOf(d).setScale(decimalPlace, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    @Step({"<key> elementinin text değeri <expectedText> değerine eşit mi",
            "get text <key> element and control <expectedText>"})
    public void controlElementText(String key, String expectedText) {

        expectedText = expectedText.endsWith("KeyValue") ? Driver.TestMap.get(expectedText).toString() : expectedText;
        //methods.waitByMilliSeconds(250);
        String actualText = methods.getText(methods.getBy(key))
                .replace("\r", "").replace("\n", "").trim();
        System.out.println("Beklenen text: " + expectedText);
        System.out.println("Alınan text: " + actualText);
        assertEquals(expectedText, actualText,"Text değerleri eşit değil");
        System.out.println("Text değerleri eşit");
    }

    @Step({"<key> elementinin text değeri <expectedText> değerini içeriyor mu",
            "get text <key> element and control contains <expectedText>"})
    public void controlElementTextContain(String key, String expectedText) {

        expectedText = expectedText.endsWith("KeyValue") ? Driver.TestMap.get(expectedText).toString() : expectedText;
        //methods.waitByMilliSeconds(250);
        String actualText = methods.getText(methods.getBy(key))
                .replace("\r", "").replace("\n", "").trim();
        System.out.println("Beklenen text: " + expectedText);
        System.out.println("Alınan text: " + actualText);
        assertTrue(actualText.contains(expectedText),"Text değerleri eşit değil");
        System.out.println("Text değerleri eşit");
    }

    @Step("<key> elementinin <attribute> niteliği <expectedValue> değerine eşit mi")
    public void checkElementAttribute(String key, String attribute, String expectedValue) {

        expectedValue = expectedValue.endsWith("KeyValue") ? Driver.TestMap.get(expectedValue).toString() : expectedValue;
        String attributeValue = methods.getAttribute(methods.getBy(key), attribute);
        assertNotNull(attributeValue,"Elementin değeri bulunamadi");
        System.out.println("expectedValue: " + expectedValue);
        System.out.println("actualValue: " + attributeValue);
        assertEquals(expectedValue, attributeValue,"Elementin değeri eslesmedi");
    }

    @Step("<key> elementinin <attribute> niteliği <expectedValue> değerini içeriyor mu")
    public void checkElementAttributeContains(String key, String attribute, String expectedValue) {

        expectedValue = expectedValue.endsWith("KeyValue") ? Driver.TestMap.get(expectedValue).toString() : expectedValue;
        String attributeValue = methods.getAttribute(methods.getBy(key), attribute);
        assertNotNull(attributeValue,"Elementin değeri bulunamadi");
        System.out.println("expectedValue: " + expectedValue);
        System.out.println("actualValue: " + attributeValue);
        assertTrue(attributeValue.contains(expectedValue)
                ,expectedValue + " elementin değeriyle " + attributeValue + " eslesmedi");
    }

    @Step("<key> mouseover element")
    public void mouseOver(String key) {

        methods.mouseOverJs(methods.getBy(key),"1");
    }

    @Step("<key> mouseout element")
    public void mouseOut(String key) {

        methods.mouseOutJs(methods.getBy(key),"1");
    }

    @Step("<key> hover element")
    public void hoverElementAction(String key) {

        methods.hoverElementAction(methods.getBy(key), true);
    }

    @Step("<key> hover element <isScrollElement>")
    public void hoverElementAction(String key, boolean isScrollElement) {

        methods.hoverElementAction(methods.getBy(key), isScrollElement);
    }

    @Step("<key> hover and click element")
    public void moveAndClickElement(String key) {

        methods.moveAndClickElement(methods.getBy(key), true);
    }

    @Step("<key> scroll element")
    public void scrollElementJs(String key) {

        methods.scrollElementJs(methods.getBy(key),"3");
    }

    @Step("<key> scroll element center")
    public void scrollElementCenterJs(String key) {

        methods.scrollElementCenterJs(methods.getBy(key),"3");
    }

    @Step("<key> focus element")
    public void focusElement(String key) {

        methods.focusElementJs(methods.getBy(key));
    }

    @Step("<key> select element by index <index>")
    public void selectElementByIndex(String key, int index) {

        methods.selectByIndex(methods.getBy(key), index);
    }

    @Step("<key> select element by value <value>")
    public void selectElementByValue(String key, String value) {

        methods.selectByValue(methods.getBy(key), value);
    }

    @Step("<key> select element by text <text>")
    public void selectElementByText(String key, String text) {

        methods.selectByVisibleText(methods.getBy(key), text);
    }

    @Step("<key> select element by index <index> js")
    public void selectElementByIndexJs(String key, int index) {

        methods.selectByIndexJs(methods.getBy(key), index,"3");
    }

    @Step("<key> select element by value <value> js")
    public void selectElementByValueJs(String key, String value) {

        methods.selectByValueJs(methods.getBy(key), value,"3");
    }

    @Step("<key> select element by text <text> js")
    public void selectElementByTextJs(String key, String text) {

        methods.selectByTextJs(methods.getBy(key), text,"3");
    }

    @Step("Open new tab <url>")
    public void openNewTabJs(String url){

        methods.openNewTabJs(url);
    }

    @Step("Open new tab <url> and go to keyValue")
    public void openNewTabJsAndGoToUrlKeyValue(String url) {

        url = url.endsWith("KeyValue") ? Driver.TestMap.get(url).toString() : url;
        methods.openNewTabJs(url);
    }

    @Step("Açılan yeni taba geçilir.")
    public void goToNewTab() {
        waitPageLoadCompleteJs();
        waitByMilliSeconds(50);
        ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(1));
        waitPageLoadCompleteJs();
        waitByMilliSeconds(50);
        // driver.close();
    }

    @Step("Switch tab <switchTabNumber>")
    public void switchTab(int switchTabNumber){

        methods.switchTab(switchTabNumber);
    }

    @Step("<key> elementi görünürse, <element> listesinden random seçim yapılır.")
    public void randomIfVisible(String key, String element){
        methods.ifVisibleSelectRandom(key,element);
    }

    @Step("<key> elementi görünürse <element> elementine tıkla ve <control> elementini kontrol et")
    public void ifElementVisibleClickAndControlStep(String key, String element, String control){

        methods.ifElementVisibleClickAndControl(key, element,control);
    }

    @Step("<key> switch frame element")
    public void switchFrameWithKey(String key) {
        methods.switchFrameWithKey(methods.getBy(key));
    }

    @Step("Switch default content")
    public void switchDefaultContent() {
        methods.switchDefaultContent();
    }

    @Step("Switch parent frame")
    public void switchParentFrame() {
        methods.switchParentFrame();
    }

    @Step("Navigate to refresh")
    public void navigateToRefresh(){

        methods.navigateToRefresh();
    }

    @Step("Navigate to back")
    public void navigateToBack(){

        methods.navigateToBack();
    }

    @Step("Navigate to forward")
    public void navigateToForward(){

        methods.navigateToForward();
    }

    @Step("Get Cookies")
    public void getCookies(){

        logger.info("cookies: " + methods.getCookies().toString());
    }

    @Step("Delete All Cookies")
    public void deleteAllCookies(){

        methods.deleteAllCookies();
    }

    @Step("Get User Agent")
    public void getUserAgent(){

        logger.info("userAgent: " + methods.jsExecuteScript("return navigator.userAgent;", false).toString());
    }

    @Step("Get page source")
    public void getPageSource(){

        logger.info("pageSource: " + methods.getPageSource());
    }

    @Step("Get performance logs <logContainText>")
    public void getPerformanceLogs(String logContainText){

        LogEntries les = driver.manage().logs().get(LogType.PERFORMANCE);
        for (LogEntry le : les) {
            //if(le.getMessage().contains("\"method\":\"Network.responseReceived\"")) {
            if(le.getMessage().contains(logContainText)){
                System.out.println(le.getMessage());
            }
        }
    }

    @Step("Get Navigator Webdriver")
    public void getNavigatorWebdriver(){

        Object object = methods.jsExecuteScript("return navigator.webdriver;", false);
        logger.info("NavigatorWebdriver: " + (object == null ? "null" : object.toString()));
    }

    @Step("Get Navigator Webdriver Json")
    public void getNavigatorWebdriverJson(){

        Object object = methods.jsExecuteScript("return Object.defineProperty(navigator, 'webdriver', { get: () => undefined });", false);
        logger.info("NavigatorWebdriverJson: " + (object == null ? "null" : object.toString()));
    }


    @Step("Sekmeyi kapat")
    public void closeTab(){
        methods.close();

    }

    @Step("Tablar kontrol edilir")
    public void tabControl(){
        for (int i = 0; i < 20; i++){
            methods.waitByMilliSeconds(400);
            if (methods.listTabs().size() > 1){
                break;
            }
        }
    }

    @Step("<key> elementi için <deltaY> deltaY <offsetX> offsetX ve <offsetY> offsetY değerleriyle mouse tekerleğini kaydır")
    public void mouseWheel(String key, int deltaY, int offsetX, int offsetY){

        methods.getJsMethods().wheelElement(methods.findElement(methods.getBy(key)), deltaY, offsetX, offsetY);
    }

    @Step("<key> elementi için <deltaY> deltaY <offsetX> offsetX ve <offsetY> offsetY değerleriyle mouse tekerleğini kaydır 2")
    public void mouseWheelSimple(String key, int deltaY, int offsetX, int offsetY){

        methods.getJsMethods().wheelElementSimple(methods.findElement(methods.getBy(key)), deltaY, offsetX, offsetY);
    }

    @Step("waitPageLoadCompleteJs")
    public void waitPageLoadCompleteJs(){

        methods.waitPageLoadCompleteJs();
    }

    @Step("<key> elementinin değerini <value> değeriyle değiştirerek <newKey> elementini oluştur")
    public void keyValueChangerMethod(String key, String value, String newKey){

        methods.keyValueChangerMethodWithNewElement(key,newKey,methods
                .getKeyValueChangerStringBuilder(value,"|!","KeyValue"),"|!");
    }

    @Step("<value> değeri <replaceValues> degerlerini temizle ve <mapKey> değerinde tut <trim>")
    public void replaceText(String value, String replaceValues, String mapKey, boolean trim){

        value = value.endsWith("KeyValue") ? Driver.TestMap.get(value).toString() : value;
        String[] splitValues = replaceValues.split("\\?!");
        for (String splitValue: splitValues){
            if (!splitValue.equals("")){
                value = value.replace(splitValue,"");
            }
        }
        value = trim ? value.trim() : value;
        Driver.TestMap.put(mapKey, value);
    }

    @Step("<value> degerini <mapKey> keyinde tut")
    public void saveData(String value, String mapKey) {

        value = value.endsWith("KeyValue") ? Driver.TestMap.get(value).toString() : value;
        Driver.TestMap.put(mapKey, value);
    }
    @Step("<key> listesinden random olarak seçim yapılır")
    public void random_Select(String key) {

        methods.randomSelect(key);
    }
    @Step("<key> listesinden random olarak js ile seçim yapılır")
    public void jsRandom_Select(String key) {

        methods.jsRandomSelect(key);
    }

    @Step("<key> elementi görünürse, <element> elementine tıkla")
    public void removeIfIsVisible(String key, String element){
        methods.removeProductIsVisible(key, element);
    }

    @Step("<key> elementi varsa, elemente <text> textini yaz ve entera bas -> ortam için")
    public void haveKeySendClick(String key, String element){
        methods.enterSendElementIsVisible(key, element);
    }

    @Step("<key> elementine dinamik kullanici adi yaz")
    public void LoginSystem(String key)
    {
        String text;
        text = ConfigurationProp.getString("userName").endsWith("KeyValue") ? Driver.TestMap.get(ConfigurationProp.getString("userName")).toString() : ConfigurationProp.getString("userName");
        methods.sendKeys(methods.getBy(key), text);
    }


    @Step("<key> elementine dinamik sifre yaz")
    public void LoginPassword(String key)
    {
        String text;
        text = ConfigurationProp.getString("password").endsWith("KeyValue") ? Driver.TestMap.get(ConfigurationProp.getString("password")).toString() : ConfigurationProp.getString("password");
        methods.sendKeys(methods.getBy(key), text);
    }


    @Step("<key> elementli searchbox'a dinamik ürün yaz-without pw controls")
    public void searchProductSystem(String key)
    {
        String text;
        text = ConfigurationProp.getString("productSearch").endsWith("KeyValue") ? Driver.TestMap.get(ConfigurationProp.getString("productSearch")).toString() : ConfigurationProp.getString("productSearch");
        methods.sendKeys(methods.getBy(key), text);
    }
    @Step("<key> elementi görünür değilse, random <element> seçime devam edilir.")
    public void bcUrunBedenBul(String key, String element){
        methods.bcSelectProductIsVisible(key,element);
    }

    @Step("<key> elementi görünürse, <element> elementli searchbox'a dinamik değer yaz ve entere bas")
    public void sendDinamicKeysElementAndEnterIfIsVisible(String key, String element) {
        if (methods.isElementVisible(methods.getBy(key),1)) {
            String text;
            text = ConfigurationProp.getString("productSearch").endsWith("KeyValue") ? Driver.TestMap.get(ConfigurationProp.getString("productSearch")).toString() : ConfigurationProp.getString("productSearch");
            methods.sendKeys(methods.getBy(key), text);
            waitByMilliSeconds(200);
            methods.sendKeysWithKeys(methods.getBy(element), "ENTER");
        }
        else{
            System.out.println("...");
        }
    }
    @Step("<key> elementi text'inde bulunan fiyat değerini ayıkla.")
    public void sayisalDegeriAyikla(String key){
        String OSNAMES= methods.findElement(methods.getBy(key)).getText();
        String[] parts = OSNAMES.split(" ");
        String OS = parts[0];
        OS=OS.replace(",",".");
        kargo_ucretsiz_fiyat=Float.parseFloat(OS);
        System.out.println(kargo_ucretsiz_fiyat);
    }
    public float kargo_ucretsiz_fiyat; //ürün detaydaki kargo ücretini toplama için float olarak tutar
    public float sepetteki_kargo_ucretsiz_fiyat; //sepetteki kargo ücretini toplama için float olarak tutar

    @Step("Ürün detaydaki kargo ücretsiz fiyat aralığının sepetteki <key> indirimli fiyatının, <element> genel toplam kargo fiyatı ve <value> sepet kargo fiyatıile uyuştuğu kontrol edilir.")
    public void dene(String key, String element, String value){
        System.out.println(kargo_ucretsiz_fiyat);
        String actualText = methods.findElement(methods.getBy(key)).getText();
        String rsActual=actualText.replace("TL","");
        String rActual=rsActual.replace(",",".");
        float fActualNew =Float.parseFloat(rActual);
        System.out.println("Ürünün İndirimli Fiyatı: " + fActualNew);
        String cargoPrice = methods.findElement(methods.getBy(element)).getText();

        if(fActualNew>=kargo_ucretsiz_fiyat){
            System.out.println("Beklenen: ÜCRETSİZ");
            System.out.println("Gerçekleşen: "+cargoPrice);
            assertEquals("ÜCRETSİZ", cargoPrice);
            System.out.println("Beklenen: Kargo Bedava");
            System.out.println("Gerçekleşen: "+ methods.findElement(methods.getBy(value)).getText());
            assertEquals("Kargo Bedava",methods.findElement(methods.getBy(value)).getText());
        }
        else {
            assertNotEquals("ÜCRETSİZ",cargoPrice);
            System.out.println(kargo_ucretsiz_fiyat);
            System.out.println("Kargo ücretsiz limiti :" + kargo_ucretsiz_fiyat
            + " sağlanmadığı için " + cargoPrice + " kargo ücreti uygulanmıştır.");
            String OSNAMES= methods.findElement(methods.getBy(value)).getText();
            String[] parts = OSNAMES.split(" ");
            String OS = parts[0];
            OS=OS.replace(",",".");
            sepetteki_kargo_ucretsiz_fiyat=Float.parseFloat(OS);
            System.out.println(sepetteki_kargo_ucretsiz_fiyat);
            assertEquals(kargo_ucretsiz_fiyat,sepetteki_kargo_ucretsiz_fiyat);
        }
    }
    //M.erkan
    @Step("<key> yoksa <key1> yoksa <key2> görünürse tıkla")
    public void RandomIfVisibleBeden(String key, String key1,String key2)
    {
        methods.ifVisibleSelectRandomBeden(key,key1,key2);
    }

    @Step("Şu anki url <sepeturl> sepet url si mi")
    public void doesUrlContainBasket(String sepeturl) {

        sepeturl = sepeturl.endsWith("KeyValue") ? Driver.TestMap.get(sepeturl).toString() : sepeturl;
        sepeturl = methodsUtil.setValueWithMap(sepeturl);
        assertTrue(methods.doesUrl(sepeturl, 80, "contain"),"Beklenen url, sayfa url ine eşit değil");
    }
    @Step("<sepeturl> dinamik adresine git")
    public void navigateToBasket(String sepeturl) {
        String text;
        text = ConfigurationProp.getString("sepeturl").endsWith("KeyValue") ? Driver.TestMap.get(ConfigurationProp.getString("sepeturl")).toString() : ConfigurationProp.getString("sepeturl");
        text = text.endsWith("KeyValue") ? Driver.TestMap.get(text).toString() : text;
        text = methodsUtil.setValueWithMap(text);
        methods.navigateTo(text);
    }
    //M.erkan
    @Step("<key> elementi görünürse, <deger> locator'lı alışverişe başla, " +
            "anasayfadaki <spor> locator'lı kategoriden random <element> ürün seç ve " +
            "<value> random beden seçerek <loca> sepete başka ürün ekle ve <sepet> sepete git.")
    public void ifvisibleAddtoChartAgain(String key, String deger, String spor, String element, String value, String loca, String sepet) {
        if (methods.isElementVisible(methods.getBy(key), 1)) {
            waitByMilliSeconds(800);
            //alışverişe başla butonuna tıklanır.
            methods.checkElementVisible(methods.getBy(deger), 30);
            methods.checkElementClickable(methods.getBy(deger), 30);
            clickElement(deger);
            waitPageLoadCompleteJs();
            waitByMilliSeconds(800);

            //kategoriye gidilir.
            methods.checkElementVisible(methods.getBy(spor), 30);
            methods.checkElementClickable(methods.getBy(spor), 30);
            clickElement(spor);
            waitByMilliSeconds(600);
            //random ürün seçilir.
            methods.checkElementVisible(methods.getBy(element), 30);
            methods.checkElementClickable(methods.getBy(element), 30);
            methods.randomSelect(element);
            waitPageLoadCompleteJs();
            waitByMilliSeconds(400);
            //random beden seçilir.
            methods.randomSelect(value);
            waitPageLoadCompleteJs();
            waitByMilliSeconds(300);
            //sepete eklenir.
            methods.checkElementVisible(methods.getBy(loca), 60);
            methods.checkElementClickable(methods.getBy(loca), 60);
            clickElement(loca);
            waitPageLoadCompleteJs();
            waitByMilliSeconds(300);
            methods.checkElementVisible(methods.getBy(sepet), 60);
            methods.checkElementClickable(methods.getBy(sepet), 60);
            clickElement(sepet);
        } else {
            logger.info("Ürün sepete başarılı bir şekilde eklenmiştir.");
        }
    }

    @Step("<key> istenilen miktarda ürün bulunmamaktadır görünürse, <element> pop-up'ı kapatılır. <kategori> elementli kategoriye gidilir, <product> locator'lı random ürün seçilir, <size> random beden seçilip <button> sepete eklenir.")
    public void stepIfThereIsNoStockAddAnother(String key, String element, String kategori, String product, String size, String button) {
        methods.ifThereIsNoStockAddAnother(key, element, kategori, product, size, button);
    }

    @Step("<key> textbox'ına dinamik 3D secure bilgisi gir ve <onay> butonu ile onayla with frame <value>")
    public void dynamicPaymentWithSmsOnay(String key, String onay, String value) {

        driver.switchTo().frame(driver.findElement(methods.getBy(value)));
        waitPageLoadCompleteJs();
        checkElementVisible(key, 10);
        String text;
        text = ConfigurationProp.getString("3DSecure").endsWith("KeyValue") ? Driver.TestMap.get(ConfigurationProp.getString("3DSecure")).toString() : ConfigurationProp.getString("3DSecure");
        driver.findElement(methods.getBy(key)).sendKeys(text);
        checkElementVisible(onay, 10);
        driver.findElement(methods.getBy(onay)).click();
        checkElementInVisible(onay, 30);
        switchParentFrame();
    }

    @Step("<key> elementi görünürse, random <text> sipariş iptali textini yaz")
    public void randomText(String key, String value) {
        Faker faker = new Faker();
        if(methods.isElementVisible(methods.getBy(key),1)) {
            String firstname = faker.name().firstName();
            String text = firstname.toLowerCase() + " olduğundan dolayı siparişimi iptal etmek istiyorum.";
            text = text.endsWith("KeyValue") ? Driver.TestMap.get(text).toString() : text;
            methods.sendKeys(methods.getBy(key), text);
        }
        else {
            logger.info("...");
        }
    }

    @Step("<key> adresli ve <value> şifreli cookie oluştur.")
    public void setNewCookie(String key, String value){
        Cookie cname = new Cookie(key, value);
        driver.manage().addCookie(cname);
    }

    @Step("<key> iframe'e girilir.")
    public void paymentWithSmsOnay(String key) {

        driver.switchTo().frame(driver.findElement(methods.getBy(key)));
    }

    @Step("<key> iframe'i görünürse, iframe'e girilir.")
    public void paymentWithSmsOnayIframeIfVisible(String key) {
        if (methods.isElementVisible(methods.getBy(key), 1)) {
            driver.switchTo().frame(driver.findElement(methods.getBy(key)));
        }
        logger.info("iframe görünür değil.");
    }

    @Step("iframe'den çıkılır.")
    public void paymentWithSmsOnay() {
        switchParentFrame();
    }

}