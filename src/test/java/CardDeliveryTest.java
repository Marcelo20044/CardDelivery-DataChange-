import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {


    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


    @Test
    public void shouldRescheduleMeeting() {
        var validUser = DataGenerator.generateByPersonalInfo();

        String planningDate = generateDate(4);
        String newDate = generateDate(5);
        Configuration.holdBrowserOpen = true;


        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $(byName("name")).val(validUser.getName());
        $(byName("phone")).val(validUser.getPhone());
        $("[class=\"checkbox__box\"]").click();
        $(byText("Запланировать")).click();

        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(newDate);
        $(byText("Запланировать")).click();
        $(byText("Перепланировать")).click();
        $("[data-test-id=\"success-notification\"]").shouldHave(Condition.text("Успешно!"));
        $(".notification__content").shouldBe(visible)
                .shouldHave(exactText("Встреча успешно запланирована на " + newDate));

    }


}
