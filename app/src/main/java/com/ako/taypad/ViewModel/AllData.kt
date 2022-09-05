package com.ako.taypad.ViewModel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.databinding.tool.util.Preconditions.check
import android.util.Log
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ako.taypad.AuthenticationActivity
import com.ako.taypad.MainActivity
import com.ako.taypad.R
import com.ako.taypad.Retrofit.testfilter
import com.ako.taypad.model.ResponseData
import com.ako.taypad.model.example.bindexample
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class AllData : ViewModel() {
    lateinit var count: MutableLiveData<Int>
    lateinit var examplae: MutableLiveData<ArrayList<bindexample>>

    init {
        count = MutableLiveData()
    }

    fun getcount(): LiveData<Int> {
        return count
    }

    fun addcount(noti: Int) {
        count.value = noti
        getcount()
    }

    fun reducecount() {
        var count = count.postValue(count.value)
        getcount()
    }

    fun getExample(): LiveData<ArrayList<bindexample>> {
        if (!::examplae.isInitialized) {
            examplae = MutableLiveData()
            val allbook = ArrayList<bindexample>()
            allbook.add(
                bindexample(
                    "Generate a book name through a book title generator",
                    "Romance",
                    "Here we pass the flags for the directions of drag and swipe. Since swipe is disable we pass 0 for it.",
                    R.drawable.d
                )
            )
            allbook.add(
                bindexample(
                    "Their Eyes Were Watching God by Zora Neale Hurston", "Horror",
                    "Based on the current state of the RecyclerView and whether ",
                    R.drawable.down
                )
            )
            allbook.add(
                bindexample(
                    "East of Eden by John Steinbeck", "Fight",
                    "Each sentence in this paragraph example relates to the feeling described in the topic sentence. Although writing in a narrative form, Northup waits until a new paragraph to continue the story—this paragraph focuses solely on that one emotion. \n" +
                            "\n" +
                            "Finally, let’s look at a fiction paragraph example. In Bram Stoker’s Dracula, one of the protagonists, Jonathan Harker, describes the appearance of Count Dracula. \n" +
                            "\n" +
                            "His face was a strong—a very strong—aquiline, with high bridge of the thin nose and peculiarly arched nostrils; with lofty domed forehead, and hair growing scantily round the temples but profusely elsewhere. His eyebrows were very massive, almost meeting over the nose, and with bushy hair that seemed to curl in its own profusion. The mouth, so far as I could see it under the heavy moustache, was fixed and rather cruel-looking, with peculiarly sharp white teeth; these protruded over the lips, whose remarkable ruddiness showed astonishing vitality in a man of his years. For the rest, his ears were pale, and at the tops extremely pointed; the chin was broad and strong, and the cheeks firm though thin. The general effect was one of extraordinary pallor.\n" +
                            "\n" +
                            "Paragraphs in fiction are more flexible with the rules, but nonetheless, this paragraph includes both a topic sentence and a concluding summary. Notice how all the details pertain to Dracula’s face and head; Stoker begins a new paragraph when describing other parts of his appearance, like his hands, because the author treats it as a separate topic. gets triggered. Here we can customize the RecyclerView row. For example, changing the background color.",
                    R.drawable.dow
                )
            )
            allbook.add(
                bindexample(
                    "Don’t forget the subtitle",
                    "Science",
                    "This method gets triggered when the user interaction stops with the ",
                    R.drawable.download
                )
            )
            allbook.add(
                bindexample(
                    "Analyze the book titles of other books", "Comdey",
                    "Let’s start building our android application with the drag and drop feature on the RecyclerView." +
                            "Example paragraphs from literature \n" +
                            "Rather than merely talk about paragraph structure, let’s look at some paragraph examples so you can see structure in action. \n" +
                            "\n" +
                            "The first paragraph example comes from Bertrand Russell in his essay “Icarus, or the Future of Science.” This excerpt uses the same paragraph structure often used in research papers, essays, and other nonfiction writing. The first sentence makes a claim, and the subsequent sentences defend that claim, ending in a strong conclusion that ties everything together. \n" +
                            "\n" +
                            "If men were rational in their conduct, that is to say, if they acted in the way most likely to bring about the ends that they deliberately desire, intelligence would be enough to make the world almost a paradise. In the main, what is in the long run advantageous to one man is also advantageous to another. But men are actuated by passions which distort their view; feeling an impulse to injure others, they persuade themselves that it is to their interest to do so. They will not, therefore, act in the way which is in fact to their own interest unless they are actuated by generous impulses which make them indifferent to their own interest. This is why the heart is as important as the head. By the “heart” I mean, for the moment, the sum-total of kindly impulses. Where they exist, science helps them to be effective; where they are absent, science only makes men more cleverly diabolic.\n" +
                            "\n" +
                            "Notice how all sentences in the paragraph relate to the same idea: That humans act emotionally more than rationally. However, each sentence makes its own unique point, and when taken together, they connect to the central topic. \n" +
                            "\n" +
                            "Another nonfiction paragraph example comes from Twelve Years a Slave, a memoir from freeborn African-American Solomon Northup who was kidnapped and forced into slavery for twelve years before friends and family intervened with the help of the law. \n" +
                            "\n" +
                            "I expected to die. Though there was little in the prospect before me worth living for, the near approach of death appalled me. I thought I could have been resigned to yield up my life in the bosom of my family, but to expire in the midst of strangers, under such circumstances, was a bitter reflection.\n" +
                            "\n" +
                            "Each sentence in this paragraph example relates to the feeling described in the topic sentence. Although writing in a narrative form, Northup waits until a new paragraph to continue the story—this paragraph focuses solely on that one emotion. \n" +
                            "\n" +
                            "Finally, let’s look at a fiction paragraph example. In Bram Stoker’s Dracula, one of the protagonists, Jonathan Harker, describes the appearance of Count Dracula. ",
                    R.drawable.images
                )
            )
            allbook.add(
                bindexample(
                    "check",
                    "check",
                    "Before we dive into paragraph structure, let’s start with paragraph meaning. " +
                            "A paragraph is an individual segment of writing that discusses a central idea, typically with more than one sentence. It even has its own paragraph symbol in copyediting," +
                            " called the pilcrow (¶), not to be confused with the section symbol called the silcrow (§) that’s common in legal code. \n" +
                            "\n" +
                            "Here we focus mainly on paragraph structure, but feel free to read our ultimate guide to paragraphs for more of the basics. \n" +
                            "\n" +
                            "Parts of a paragraph\n" +
                            "Like other forms of writing, paragraphs follow a standard three-part structure with a beginning, middle, and end. These parts are the topic sentence, development and support, and conclusion. \n" +
                            "\n" +
                            "Topic sentences, also known as “paragraph leaders,” introduce the main idea that the paragraph is about. They shouldn’t reveal too much on their own, but rather prepare the reader for the rest of the paragraph by stating clearly what topic will be discussed. \n" +
                            "\n" +
                            "The development and support sentences act as the body of the paragraph. Development sentences elaborate and explain the idea with details too specific for the topic sentence, while support sentences provide evidence, opinions, or other statements that back up or" +
                            " confirm the paragraph’s main idea. \n" +
                            "\n" +
                            "Last, the conclusion wraps up the idea, sometimes summarizing what’s been presented or transitioning to the next paragraph. The content of the conclusion depends on the type of paragraph, and it’s often acceptable to end a paragraph with a final piece of support that concludes the thought instead of a summary. \n" +
                            "\n" +
                            "How many sentences are in a paragraph?\n" +
                            "Most paragraphs contain between three and five sentences, but there are plenty of exceptions. Different types of paragraphs have different numbers of sentences, like those in narrative writing, in particular, where single-sentence paragraphs are common.\n" +
                            "\n" +
                            "Likewise, the number of sentences in a paragraph can change based on the style of the writer. Some authors prefer longer, more descriptive paragraphs, while other authors prefer shorter, faster-paced paragraphs. \n" +
                            "\n" +
                            "When it comes to nonfiction writing, like research papers or reports, most paragraphs have at least three sentences: a topic sentence, a development/support sentence, and a conclusion sentence. \n" +
                            "\n" +
                            "Types of paragraphs \n" +
                            "Depending on the kind of writing you’re doing, you may need to use different types of paragraphs. Here’s a brief explanation of the common paragraph types most writing deals with. \n" +
                            "\n" +
                            "Expository: Common in nonfiction and all types of essays, expository paragraphs revolve around explaining and discussing a single point or idea. \n" +
                            "Persuasive: Just like expository paragraphs, persuasive paragraphs focus on discussing a single point; however, they support opinions instead of facts. \n" +
                            "Narrative: When telling a story, a narrative paragraph explains an action or event. Each new sentence furthers or expands upon the action by providing new information. \n" +
                            "Descriptive: Also common in storytelling, descriptive paragraphs focus on describing a single topic, such as a person or an environment. Each new sentence adds a new detail about that topic. \n" +
                            "The type of paragraph used usually depends on the type of writing. For example, if you’re writing a research paper, it would be difficult to justify a narrative paragraph. ",
                    R.drawable.images
                )
            )
            allbook.add(
                bindexample(
                    "The Best Seller", "Romance",
                    "Types of paragraphs \n" +
                            "Depending on the kind of writing you’re doing, you may need to use different types of paragraphs. Here’s a brief explanation of the common paragraph types most writing deals with. \n" +
                            "\n" +
                            "Expository: Common in nonfiction and all types of essays, expository paragraphs revolve around explaining and discussing a single point or idea. \n" +
                            "Persuasive: Just like expository paragraphs, persuasive paragraphs focus on discussing a single point; however, they support opinions instead of facts. \n" +
                            "Narrative: When telling a story, a narrative paragraph explains an action or event. Each new sentence furthers or expands upon the action by providing new information. \n" +
                            "Descriptive: Also common in storytelling, descriptive paragraphs focus on describing a single topic, such as a person or an environment. Each new sentence adds a new detail about that topic. \n" +
                            "The type of paragraph used usually depends on the type of writing. For example, if you’re writing a research paper, it would be difficult to justify a narrative paragraph. \n" +
                            "\n" +
                            "Example paragraphs from literature ",
                    R.drawable.one
                )
            )
            allbook.add(
                bindexample(
                    "Example paragraphs from literature", "Horror",
                    "If men were rational in their conduct, that is to say, if they acted in the way most likely to bring about the ends that they deliberately desire, intelligence would be enough to make the world almost a paradise. In the main, what is in the long run advantageous to one man is also advantageous to another. But men are actuated by passions which distort their view; feeling an impulse to injure others, they persuade themselves that it is to their interest to do so. They will not, therefore, act in the way which is in fact to their own interest unless they are actuated by generous impulses which make them indifferent to their own interest. This is why the heart is as important as the head. By the “heart” I mean, for the moment, the sum-total of kindly impulses. Where they exist, science helps them to be effective; where they are absent, science only makes men more cleverly diabolic.\n" +
                            "\n" +
                            "Notice how all sentences in the paragraph relate to the same idea: That humans act emotionally more than rationally. However, each sentence makes its own unique point, and when taken together, they connect to the central topic",
                    R.drawable.two
                )
            )
            allbook.add(
                bindexample(
                    "The Second One", "Comdey",
                    "Another nonfiction paragraph example comes from Twelve Years a Slave, a memoir from freeborn African-American Solomon Northup who was kidnapped and forced into slavery for twelve years before friends and family intervened with the help of the law. \n" +
                            "\n" +
                            "I expected to die. Though there was little in the prospect before me worth living for, the near approach of death appalled me. I thought I could have been resigned to yield up my life in the bosom of my family, but to expire in the midst of strangers, under such circumstances, was a bitter reflection.",
                    R.drawable.three
                )
            )
            allbook.add(
                bindexample(
                    "The Last One", "Science",
                    "Each sentence in this paragraph example relates to the feeling described in the topic sentence. Although writing in a narrative form, Northup waits until a new paragraph to continue the story—this paragraph focuses solely on that one emotion. \n" +
                            "\n" +
                            "Finally, let’s look at a fiction paragraph example. In Bram Stoker’s Dracula, one of the protagonists, Jonathan Harker, describes the appearance of Count Dracula. \n" +
                            "\n" +
                            "His face was a strong—a very strong—aquiline, with high bridge of the thin nose and peculiarly arched nostrils; with lofty domed forehead, and hair growing scantily round the temples but profusely elsewhere. His eyebrows were very massive, almost meeting over the nose, and with bushy hair that seemed to curl in its own profusion. The mouth, so far as I could see it under the heavy moustache, was fixed and rather cruel-looking, with peculiarly sharp white teeth; these protruded over the lips, whose remarkable ruddiness showed astonishing vitality in a man of his years. For the rest, his ears were pale, and at the tops extremely pointed; the chin was broad and strong, and the cheeks firm though thin. The general effect was one of extraordinary pallor.",
                    R.drawable.four
                )
            )
            allbook.add(
                bindexample(
                    "What is Science", "18+",
                    "What are the keys to a strong paragraph? \n" +
                            "A strong paragraph explores a single topic with details following in a logical order. Paragraphs often use transitions to connect otherwise disjointed sentences, helping every piece of information to work together. \n" +
                            "\n" +
                            "How is a paragraph structured?\n" +
                            "Good paragraphs begin with a topic sentence that briefly explains what the paragraph is about. Next come a few sentences for development and support, elaborating on the topic with more detail. Paragraphs end with a conclusion sentence that summarizes the topic or presents one final piece of support to wrap up. \n",
                    R.drawable.five
                )
            )
            examplae.value = allbook

        }
        return examplae
    }
//    lateinit var alldata: MutableLiveData<Stories>
//    fun getdata(jwt:String) : LiveData<Stories>{
//        if(! :: alldata.isInitialized){
//            alldata= MutableLiveData()
//            val getapi= testfilter.JsonApi.get("Bearer "+jwt)
//            getapi.enqueue(object : Callback<Stories>{
//                override fun onResponse(
//                    call: Call<Stories>,
//                    response: Response<Stories>
//                ) {
//                   alldata.value=response.body()
//                }
//                override fun onFailure(call: Call<Stories>, t: Throwable) {
//                }
//            })
//        }
//        return alldata
//    }
}