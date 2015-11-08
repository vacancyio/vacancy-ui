package analysis

import model.Job

object JobAnalysis {

  def words(input: String) = input.split(" ").toList.map(_.toLowerCase).toSet

  val languages = Set("clojure", "haskell", "scala", "java", "javascript", "python", "ruby")

  val frameworks = Set("ruby on rails", "spring boot", "spring", "node")

  val allTags = languages ++ frameworks

  /** Returns true if an input contains a given string expr */
  def contains(input: String, expr: String): Boolean =
    input.toLowerCase.matches(s".*${expr.toLowerCase}.*")

  /**
   * Given a string and a set of tags, extract any words in the string that also exist
   * in the tag set
   *
   * @param input An input string
   * @param tagSet A set of tags to extract
   *
   * @return A set of tags for this input
   */
  def extract(input: String, tagSet: Set[String]): Set[String] =
    tagSet.foldLeft(Set.empty[String]){ (xs, x) =>
      if (contains(input, x)) xs + x else xs }

  def tagsForJob(job: Job): Set[String] = {
    val tags = extract(job.description, allTags) ++ extract(job.title, allTags) ++ extract(job.skills.getOrElse(""), allTags)
    tags.map(_.capitalize)
  }
}
