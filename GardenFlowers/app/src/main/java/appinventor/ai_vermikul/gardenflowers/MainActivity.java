package appinventor.ai_vermikul.gardenflowers;


import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import android.content.Intent;
//import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Random;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private String fileSaveName = "";
    private String storageDir;
    private Bitmap bm;

    ImageView imageView;
    TextView textView;
    Button next,previous,more;

    String language = Locale.getDefault().getISO3Language();

    private int[] imageIds = {R.drawable.i1, R.drawable.i2, R.drawable.i3, R.drawable.i4, R.drawable.i5,
            R.drawable.i6, R.drawable.i7, R.drawable.i8, R.drawable.i9, R.drawable.i10,
            R.drawable.i11, R.drawable.i12, R.drawable.i13, R.drawable.i14, R.drawable.i15,
            R.drawable.i16, R.drawable.i17, R.drawable.i18, R.drawable.i19, R.drawable.i20,
            R.drawable.i21, R.drawable.i22, R.drawable.i23, R.drawable.i24, R.drawable.i25,
            R.drawable.i26, R.drawable.i27, R.drawable.i28, R.drawable.i29, R.drawable.i30,
            R.drawable.i31, R.drawable.i32, R.drawable.i33, R.drawable.i34, R.drawable.i35,
            R.drawable.i36, R.drawable.i37, R.drawable.i38, R.drawable.i39, R.drawable.i40,
            R.drawable.i41, R.drawable.i42, R.drawable.i43, R.drawable.i44, R.drawable.i45,
            R.drawable.i46};
    private String[] textImg = {
            "1.мальва 2.декоративный подсолнух 3.мак 4.лобелия 5.гелениум 6.котовник 7.колокольчик 8.катананхе 9.гравилат 10.очиток 11.гейхера",
            "1.георгина 2.дельфиниум 3.монарда 4.дубровник",
            "1.бамбук 2.ирис 3.лобелия 4.живучка ползучая 5.колокольчик 6.обриета 7.брунера 8.мшанка 9.живучка ползучая",
            "1.шпорник 2.бузульник 3.анхуза 4.мордовник 5.лилия 6.флокс 7.тысячелистник 8.гравилат 9.шпорник 10.лапчатка 11.вероника",
            "1.аквилегия 2.лаватера 3.мискантус 4.лилейник 5.гайлардия 6.чайногибридная роза 7.шалфей 8.солнцецвет 9.тимьян",
            "1.малорослая калина 2.мак 3.вербена 4.тысячелистник 5.кустовая роза 6.живокость 7.перистощетинник лисохвостовидный 8.пентстемон 9.астра 10.табак душистый 11.календула 12.шалфей 13.бархатцы",
            "1.сосна горная 2.тимьян ползучий 3.флокс 4.очиток Эверса 5.овсяница 6.алиссум 7.лаванда 8.очиток видный 9.рута душистая 10.полынь",
            "1.ива белая 2.лох серебристый 3.василистник 4.аир 5.валериана 6.сусак зонтичный 7.лилейник желтый 8.ирис болотный 9.ирис сибирский 10.купальница",
            "1.дурман 2.бархатцы 3.агератум 4.левкой седой 5.лобулярия",
            "1.солнцецвет 2.гвоздика 3.прострел 4.флокс шиловидный 5.эдельвейс 6.кореопсис 7.гайлардия 8.тысячелистник 9.мак восточный 10.флокс метельчатый 11.хризантема 12.живокость 13.дельфиниум",
            "1.клематис 2.барбарис Pink Queen 3.барбарис Bonanza Gold 4.лещина 5.зопник 6.лаванда 7.чистец 8.овсяница",
            "1.церцидифиллюм 2.ирга 3.мискантус белый 4.мискантус желтый 5.монарда 6.мордовник 7.вейник 8.аконит 9.гелениум 10.вербена 11.рудбекия 12.просо прутьевидное 13.эхинацея 14.калина 15.анемоны 16.луговик 17.молиния 18.горянка 19.хаконехлоя 20.гейхера 21.осока 22.бархатцы 23.астра 24.прострел 25.очиток",
            "1.мордовник 2.вероникструм 3.колосняк песчаный 4.космос дваждыперистый 5.георгина 6.синеголовник 7.флокс метельчатый 8.полынь Людовика 9.антирринум большой 10.агератум высокорослый 11.котовник 12.агератум низкорослый 13.бархатцы 14.дерен белый 15.гелиотроп 16.петуния",
            "1.нивяник 2.флокс метельчатый 3.золотарник 4.астра 5.энотера 6.колокольчик",
            "1.астильба 2.бадан 3.барвинок 4.бруннера 5.бузульник 6.живучка ползучая 7.примула 8.роджерсия 9.хоста",
            "1.вероника 2.молочай 3.пенстемон 4.тысячелистник 5.физостегия",
            "1.шалфей 2.туя 3.клематис 4.роза 5.петуния 6.манжетка",
            "1.тимьян 2.ромашка 3.кошачья лапка 4.туя и кипарис 5.роза 6.лилия 7.котовник 8.лаванда 9.шалфей 10.мята и мелисса 11.жимолость",
            "Кипарис‚ самшит‚ скиммия‚ лавровишня‚ бересклет‚ пиерис‚ плющ‚ клематис‚ очиток‚ астры‚ астильба‚ вероника‚ бадан‚ гейхера",
            "Кипарис‚ клен‚ рододендрон‚ василистник‚ примула японская‚ вербейник‚ карликовые сосны‚ тсуга‚ ирис‚ ситник‚ незабудка‚ ветреница‚ пролески‚ папоротник‚ слива",
            "1.тис 2.плетистая роза 3.лаватера 4.полынь 5.шалфей 6.лаванда 7.солнцецвет 8.сантолина 9.розмарин 10.ромашка 11.тимьян лимонный 12.тимьян ползучий",
            "1.бересклет 2.пузыреплодник золотистый 3.василек крупноголовчатый 4.гвоздика перистая 5.гейхера 6.кореопсис 7.лилейник 8.герань 9.монарда 10.лабазник 11.дельфиниум 12.рудбекия 13.вербейник 14.нивяник 15.хризантема 16.любисток 17.душица 18.мелисса",
            "1.роза 2.чубушник 3.шпорник 4.флокс метельчатый 5.колокольчик персиколистный 6.мелколепестник 7.герань величественная 8.пиренейская герань 9.колокольчик 10.манжетка мягкая",
            "1.золотарник 2.мелколепестник 3.полынь пурша 4.лихнис халкедонский 5.гелениум 6.колокольчик молочноцветковый 7.посконник 8.колеус",
            "1.герань луговая 2.флокс метельчатый 3.мелколепестник 4.колокольчик Пожарского",
            "1.пенстемон 2.живокость 3.кореопсис 4.нивяник великолепный",
            "1.лиатрис колосковый 2.очиток скальный 3.шалфей 4.астра 5.очиток скрипун 6.просо прутьевидное 7.вероника 8.тысячелистник таволговый 9.шток.роза 10.подсолнечник однолетний 11.клематис 12.плетистая роза 13.будлея Давида 14.рудбекия 15.астра новобельгийская 16.тысячелистник 17.рудбекия 18.астра 19.перистощетинник 20.колокольчик Пожарского",
            "1.герань 2.манжетка мягкая 3.герань величественная 4.кореопсис 5.лапчатка 6.тысячелистник 7.декоративный лук 8.многоколосник фенхелевый 9.вербена бонарская 10.самшит 11.полынь 12.мята 13.эхинацея 14.лаватера 15.клематис",
            "1.экзохорда 2.лаванда 3.манжетка мягкая 4.герань 5.тысячелистник 6.шалфей 7.скабиоза 8.дельфиниум 9.мак восточный 10.василек 11.гипсофила 12.белый колокольчик карпатский 13.плетистая роза 14.клематис",
            "1.прострел 2.котовник 3.прунелла 4.кореопсис 5.астра 6.бадан 7.манжетка мягкая 8.рудбекия 9.нивяник 10.купальница 11.пижма багряная 12.калина",
            "1.Спирея японская 2.Армерия приморская 3.Клевер горный 4.Тюльпан Грейга 5.Тюльпан превосходный 6.Примула Юлии 7.Примула обыкновенная 8.Гравилат чилийский 9.Флокс шиловидный 10.Живучка ползучая 11.Дюшенея индийская 12.Бадан гибридный 13.Гвоздика альпийская 14.Гейхера кроваво-красная 15.Маргаритка многолетняя 16.Молодило паутинистое 17.Фиалка Витрокка 18.Вербена гибридная 19.Астильба японская 20.Портулак крупноцветковый",
            "1.Настурция 2.Душистый горошек 3.Гипсофила метельчатая 4.Гейхера 5.Петуния 6.Астра 7.Алиссум 8.Кохия 9.Флокс 10.Долихос 11.Пионы 12.Монарда",
            "1.Мощеная дорожка 2.Скамья для отдыха 3.Плетеный забор 4.Арка с цветущими растениями 5.Бордюр из салата, шнитт-лука и бархатцев 6.Пирамида из лозы для фасоли и огурцов 7.Зеленные культуры в центре (базилик, петрушка, салат) 8.Декративная и савойская капуста 9.Декоративный перец 10.Томаты с разноцветными плодами 11.Виноград на шпалере 12.Пальметта из яблонь",
            "1.Барбарис 2.Клумбовая роза 3.Плетистая роза 4.Ирга 5.Водосбор обыкновенный 6.Астра голубоватая 7.Манжетка мягкая 8.Ирис бородатый 9.Георгина-миньон 10.Кактусовая георгина 11.Молочай полихромный 12.Герань кроваво-красная 13.Клумбовая роза",
            "1.Клематис 2.Калина бульденеж 3.Очиток 4.Спирея  5.Герань 6.Декоративный лук гигантский 7.Тимьян, или чабрец лимоннопахнущий 8.Жимолость 9.Туя западная 10.Барвинок 11.Клен американский 12.Солнцецвет 13.Кизильник или бересклет",
            "1.Овсяница сизая 2.Чистец шерстистый 3.Пупавка красильная  4.Волоснец песчаный  5.Вербена жесткая 6.Коровяк гибридный  7.Вербена буйнос-айреская 8.Канна индийская",
            "1.Подсолнечник однолетний 2.Эхинацея пурпурная 3.Нивяник наибольший 4.Бархатцы отклоненные 5.Маргаритка многолетняя",
            "1.Кукуруза обыкновенная 2.Дельфиниум культурный 3.Подсолнечник однолетний 4.Флокс метельчатый 5.Кохия веничная 6.Кореопсис красильный 7.Шалфей мутовчатый 8.Тысячелистник таволговый 9.Петуниягибридная 10.Лобелия тончайшая 11.Молуцелла гладкая 12.Флокс Друммонда 13.Лук гигантский 14.Анхуза капская",
            "1.Манжетка мягкая 2.Молочай кипарисовый 3.Пион садовый 4.Люпин многолистный 5.Золотарник гибридный 6.Герань кроваво-красная 7.Лилейник буро-желтый",
            "1.Лилейник лимонно-желтый 2.Бруннера крупнолистная 3.Страусник обыкновенный 4.Колокольчик широколистный 5.Медуница сахарная 6.Астильба Арендса 7.Хоста Зибольда",
            "1.Подсолнечник однолетний 2.Колеус Блюме 3.Настурция большая 4.Бархатцы отклоненные 5.Календула лекарственная",
            "1.Перистощетинник лисохвостный 2.Астранция 3.Перистощетинник сизый 4.Колосняк 5.Хризантема 6.Вереск(сорта) 7.Георгина 8.Можжевельник 9.Мискантус 10.Вереск обыкновенный 11.Виноградовник аконитолистный 12.Гортензия  13.Клеома  14.Каллистефус  15.Цинерария",
            "1.Гравилат 2.Примула 3.Георгина Веселые ребята 4.Флокс метельчатый 5.Гейхера 6.Аквилегия 7.Астильба 8.Бегония 9.Пион 10.Клопогон 11.Флокс Друммонда 12.Гвоздика турецкая13.Антирринум большой 14.Целозия серебристая гребенчатая 15.Ирис  16.Барбарис 17.Пузыреплодник",
            "1.Прострел 2.Можжевельник казацкий 3.Хионодокса Люцилии 4.Примула высокая 5.Ива  6.Пузыреплодник  7.Крокус Pickwick 8.Крокус Yellow Mammoth 9.Крокус Jeanne d'Ark 10.Крокус Remembrance",
            "1.Белоцветник весенний 2.Пролеска сибирская 3.Хионодокса гигантская PinkGiant 4.Хионодокса гигантская Alba 5.Пушкиния пролесковидная 6.Мускари армянский 7.Подснежник снежный 8.Крокус Grand Yellow 9.Крокус Purple Sensation 10.Крокус Jeanne d'Arc 11.Крокус Pickwick 12.Крокус Remembrance 13.Нарцисс(сорта) 14.Рябчик",
            "1.Агератум Хоустока 3.Бархатцы отклоненные 4.Бархатцы прямостоячие 5.Очиток видный 6.Рудбекия 7.Георгина 8.Подсолнечник 9.Золотарник гибридный форма плакучая 10.Лилейник буро-желтый 11.Золотарник гибридный форма низкая о — Гладиолус гибридный □ — Шалфей сверкающий"
    };

    int imgLength = imageIds.length;
    int imgPosition = (new Random()).nextInt(imgLength);

   static final String POSITION = "position";

    private InterstitialAd interstitial;

    //сохраням значение imgPosition при смене ориентации экрана
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(POSITION,imgPosition);
        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId("ca-app-pub-9595835527986323/6045616678");

        /*это отладка
        AdRequest adRequest2 = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();*/

        AdRequest adRequest2 = new AdRequest.Builder().build();
        interstitial.loadAd(adRequest2);

        imageView = (ImageView) findViewById(R.id.imageView1);
        textView = (TextView) findViewById(R.id.textView);
        next = (Button) findViewById(R.id.bNext);
        previous = (Button) findViewById(R.id.bPrevious);
        more = (Button) findViewById(R.id.bMore);

        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        more.setOnClickListener(this);

        if (savedInstanceState != null) imgPosition = savedInstanceState.getInt(POSITION);

        setNewItem();

        // еще надо добавить android.permission.WRITE_EXTERNAL_STORAGE в manifest!
        storageDir = Environment.getExternalStorageDirectory().toString() + "/garden";

        //Toast.makeText(this, language, Toast.LENGTH_SHORT).show();

    }

    // выбираем новое фото и подпись
    public void setNewItem() {
        imageView.setImageResource(imageIds[imgPosition]);
        textView.setText(textImg[imgPosition]);
        bm = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
    }

    public void displayInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bPrevious:
                imgPosition--;
                if (imgPosition < 0) imgPosition = imgLength - 1;
                setNewItem();
                break;

            case R.id.bNext:
                imgPosition++;
                if (imgPosition >= imgLength) imgPosition = 0;
                setNewItem();
                if(imgPosition%10 == 0) displayInterstitial();
                break;

            case R.id.bMore:
                Intent intent;
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://idealsad.com/"));
                startActivity(intent);
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        fileSaveName = "GardenFlowers_" + imgPosition + ".jpg";

        displayInterstitial();

        switch (item.getItemId()) {
            case R.id.action_share:
                //doShare(storageDir, fileSaveName);
                Intent intentSend = new Intent(Intent.ACTION_SEND);
                intentSend.setType("text/plain");
                intentSend.putExtra(Intent.EXTRA_TEXT, "Твой цветник: https://play.google.com/store/apps/details?id=appinventor.ai_vermikul.GardenFlowers");
                startActivity(Intent.createChooser(intentSend, "Поделиться"));
                return true;
            case R.id.action_save:
                saveToSDCard(storageDir, fileSaveName);
                return true;
            case R.id.action_rate:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=appinventor.ai_vermikul.GardenFlowers"));
                startActivity(intent);
                return true;
            case R.id.action_cancel:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveToSDCard(String dir, String fileName) {
        File file1 = new File(dir);
        file1.mkdirs();
        File file2 = new File(file1, fileName);

        FileNotFoundException exception1;
        label26: {
            IOException exception2;
            label25: {
                FileOutputStream stream;
                try {
                    stream = new FileOutputStream(file2);
                } catch (FileNotFoundException exception3) {
                    exception1 = exception3;
                    break label26;
                } catch (IOException exception4) {
                    exception2 = exception4;
                    break label25;
                }

                try {
                    this.bm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    stream.flush();
                    stream.close();
                    Toast.makeText(this, "Сохранено в " + this.storageDir + "/" + this.fileSaveName, Toast.LENGTH_SHORT).show();
                    return;
                } catch (FileNotFoundException exception5) {
                    exception1 = exception5;
                    break label26;
                } catch (IOException exception6) {
                    exception2 = exception6;
                }
            }

            exception2.printStackTrace();
            Toast.makeText(this, exception2.toString(), Toast.LENGTH_SHORT).show();
            return;
        }

        exception1.printStackTrace();
        Toast.makeText(this, exception1.toString(), Toast.LENGTH_SHORT).show();

    }
}
