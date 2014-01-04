package com.droidfactory.ladoo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

public class DetailView extends Activity {

	String name = "";
	Long time = 0l;
	String current_loc = "";
	ArrayList<String> article_contents;
	ArrayList<Integer> article_images;
	private int position;
	String[] titles = { "", "3 Days to Kill", "Kaley Cuoco",
			"Welcome to Yesterday", "Academy Awards",
			"Best Film Trailers of All Times",
			"Do Movie Critics Play a Significant..", "Lady Gaga" };
	private ShareActionProvider mShareActionProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		article_contents = new ArrayList<String>();
		article_images = new ArrayList<Integer>();
		String article_content_one = "Truly, winning an Academy Award or simply being nominated for one is considered to be a significant achievement by most members of professions associated with making movies. For each and all, an award brings peer recognition, fame, prestige, pride, power and sometimes dramatic financial gains. Several indicate the financial benefits enjoyed as a result of being nominated for an Academy Award. The recent statistics also confirm that in the last 15 years, the increased revenues from winning an Academy Award for best Picture have been estimated at $ 5,000,000 to $30,000,000 in the United States and Canada.";
		String article_content_two = "Well known and widely popular starlets including Gerard Butler, Val Kilmer, Melanie Griffith and Antonio Banderas were spotted checking out Formula 1 race cars on the start grid of the U.S. Grand Prix in Austin. The clan stopped by before heading to watch driver Sebastian Vettel set a record eight in a row victory on Sunday, November 17, 2013. Last year’s inaugural race also attracted several popular names including George Lucas and Ron Howard reports THR. "
				+ "\n"
				+ "According to sources, The Grand Prix event also inspired relativity down-to-earth city with a lot of serious bling this past weekend. Various glamorous starlets were also present to add even more charm and excitement to the event. The names include Adrian Grenier, Joy Bryant, Jennie Garth and Chef Gordon Ramsay that were spotted at the Amber Lounge, where rapper Taboo from the Black Eyed Peas gave a heartfelt performance. The main aim of the event is to help burnish the F1 brand in the U.S. Also Oscar-winning sound designer Per Hallberg was at the race visiting friends who were involved with the Marussia F1 team. The Swede also added that it was his fist Grand Prix visit, though he’s been a fan of the sport for years. In an interview he also mentioned “I’ve always wanted to go the Monaco Grand Prix, but I’m almost always busy on a film when that race happens, he said. “I decided it was finally time for me to go. And Austin is closer to Los Angeles.” But Hallberg was quick to add he’ll be straight back to work on New Line’s Into the Storm on Monday morning."
				+ "\n"
				+ "The grand event at Austin also additionally put on free Fan Fest-four nights of live concerts on multiple outdoor stages around downtown, with Los Lobos and Lou Gramm among the performances."
				+ "\n"
				+ "Hollywood is totally supporting the F1 event, Are you?";
		String article_content_three = "Here comes a big offer for the writers as recently revealed news states that nine U.S. screenwriters will be handed with the opportunity to spend a year in China by the America Film Institute and a new scholarship program at the AFI Conservatory. The lucky bunch will write a feature –length screenplay and receive a full scholarship for their second year at the AFI Conservatory, where they may develop the work reports Variety."
				+ "\n"
				+ "As per sources, the scholarships are distributed by the well known Chinese businessman and Chairman of investment group IDG Greater China, Hugo Shong. In an interview he stated his part by saying “Although China has a remarkable and distinguished history stretching back more than 5,000 years, too many Americans only know Chinese culture through animated films like ‘Kung Fu Panda’ and Mulan’, said Shong. He also added by saying “I want to get to a point where Americans are familiar enough with Chinese Culture that they can write original screenplays on Chinese subjects. “Americans deserve to see other types of movies about China, ones that hopefully can entertain them, educate them and at the same time touch their hearts.";
		String article_content_four = "The grand event at the Ebell Theater also known as the 7th Annual Hamilton behind the Camera Awards, an honor for which the actors need not apply except as a presenter. The event mainly centers on the production designers, casting directors, and even the property masters. Various awards were distributed at the gala evening that included David O. Russell (American Hustle) for directing, Brad Ingelsby and Scott Cooper (Out of the Furnace) for writing Saudi Arabia’s Haifaa Al Mansour (Wajda) for Foreign Film and Robbie Brnner and Rachel Winter (Dallas Buyers Club)  for producing reports Deadline."
				+ "\n"
				+ "For each, the award ceremony turned even more memorable for reasons the organizers probably did not imagine ever. Several deserving candidates were offered and presented awards on stage. Numerous actors showed up, out of which Casey Affleck was one amongst the huge stardom who was excited about the early trade reviews from the intense film’s AFI Fest premiere the night before. Several media personals also lined up for a quick question and answer round with the team to know extra facts of the upcoming drama. While in return Cassey replied by saying, the movie is a personal one that is really about examining the times in which we live over the past five years in a country that he says is the most violent on earth.";
		String article_content_five = "The most talked about event of the year, Vanity Fair’s March 2, 2014 party is still considering locations as it will not be held at Sunset Tower, site of the magazine’s glittery Oscar party since 2009.  In a recently revealed news, VF editor Graydon Carter claimed to be looking for outdoor facility in a nearby Sunset Plaza, a West Hollywood parcel boasting sweeping city views similar to those of hotel the publication is leaving behind reports THR. Also the news is confirmed as the Sunset Tower owner Jeff Klein recently informed his staff of Carter’s decision."
				+ "\n"
				+ "The main reason behind the change of the venue is said to be motivated by a desire to accommodate a larger party. According to sources, Vanity Fair’s guest list rises above and beyond 1,000 people. Before Sunset Tower, the party was held at the much larger Morton in West Hollywood. As per the event organizers, this time the eve is set to be a much more intimate affair than in years past.";
		String article_content_six = "To host this year’s Golden Globes Award function, hosts Tina Fey and Amy Poehler will not be granted millions as the pair will be given a slightly lower six figure amount. Tina and Fey are widely known for their excellent job on stage while the pair has successfully hosted the last year’s Golden Globes as well. The women are also into an agreement to host the popular show for the next two years as informed by the sources. As announced recently, despite Radar’s claim otherwise, it turns out the pair Fey and Poehelr will sadly not be raking in millions each for both of their upcoming Golden Globes gig reports Vulture."
				+ "\n"
				+ "Radar previously reported that Fey and Poehler would take home as much multi-million dollar packages after hosting the event. A source also said “Tina and Amy decided to ask for the craziest package ever given to award hosts,” The news also informs that each are being paid more than any Oscar host in the history with the exception of Billy Crystal. The ladies are also getting large numbers of perks and packages including the use of NBC/Comcast private planes as they prepare for the awards and full freight on their entourages, who will be backstage to support during the live broadcasts. Truly, the ladies have taken full advantage of the show makers as they realized the importance and need of their flawless act.";
		String article_content_seven = "Truly, winning an Academy Award or simply being nominated for one is considered to be a significant achievement by most members of professions associated with making movies. For each and all, an award brings peer recognition, fame, prestige, pride, power and sometimes dramatic financial gains. Several indicate the financial benefits enjoyed as a result of being nominated for an Academy Award. The recent statistics also confirm that in the last 15 years, the increased revenues from winning an Academy Award for best Picture have been estimated at $ 5,000,000 to $30,000,000 in the United States and Canada.";
		article_contents.add(article_content_seven);
		article_contents.add(article_content_one);
		article_contents.add(article_content_two);
		article_contents.add(article_content_three);
		article_contents.add(article_content_four);
		article_contents.add(article_content_five);
		article_contents.add(article_content_six);
		article_contents.add(article_content_seven);
		article_images.add(R.drawable.img_five);
		article_images.add(R.drawable.img_one);
		article_images.add(R.drawable.img_two);
		article_images.add(R.drawable.img_three);
		article_images.add(R.drawable.img_four);
		article_images.add(R.drawable.img_five);
		article_images.add(R.drawable.img_six);
		article_images.add(R.drawable.img_seven);
		position = getIntent().getIntExtra("position", 0);
		ViewHolder vh = new ViewHolder();
		setViewHolder(vh);
		populateView(vh);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
		MenuItem shareItem = menu.findItem(R.id.menu_share);
		mShareActionProvider = (ShareActionProvider) shareItem
				.getActionProvider();
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		shareIntent.putExtra(Intent.EXTRA_SUBJECT, titles[position]);
		shareIntent.putExtra(Intent.EXTRA_TEXT, article_contents.get(position));
		shareIntent.setType("*/*");
		mShareActionProvider.setShareIntent(shareIntent);

		return super.onCreateOptionsMenu(menu);
	}

	private Intent getDefaultIntent() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/*");
		return intent;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// fragmentCommunicator not allowed to call when mPager is active

		return super.onPrepareOptionsMenu(menu);
	}

	private void populateView(ViewHolder vh) {
		getActionBar().setTitle(titles[position]);
		vh.txt1.setText(titles[position]);
		vh.txt2.setText(article_contents.get(position));
		vh.image_one.setImageDrawable(getResources().getDrawable(
				article_images.get(position)));
		SlidingUpPanelLayout layout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
		layout.setDragView(vh.txt1);
	}

	private void setViewHolder(ViewHolder vh) {
		vh.txt1 = (TextView) findViewById(R.id.header_text);
		vh.txt2 = (TextView) findViewById(R.id.desc_text);
		vh.image_one = (ImageView) findViewById(R.id.header_image);
	}

	class ViewHolder {
		TextView txt1;
		TextView txt2;
		TextView txt3;
		ImageView image_one;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slideinright, R.anim.slideoutright);
	}

}
