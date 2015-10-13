package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object DBTables extends {
  val profile = scala.slick.driver.MySQLDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: scala.slick.driver.JdbcProfile
  import profile.simple._
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import scala.slick.jdbc.{GetResult => GR}
  
  /** DDL for all tables. Call .create to execute. */
  lazy val ddl = Users.ddl
  
  /** Entity class storing rows of table Users
   *  @param userId Database column user_id DBType(INT), PrimaryKey, Default(0)
   *  @param name Database column name DBType(VARCHAR), Length(255,true), Default(None)
   *  @param regKey Database column reg_key DBType(VARCHAR), Length(150,true), Default(None) */
  case class UsersRow(userId: Int = 0, name: Option[String] = None, regKey: Option[String] = None)
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[UsersRow] = GR{
    prs => import prs._
    UsersRow.tupled((<<[Int], <<?[String], <<?[String]))
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag) extends Table[UsersRow](_tableTag, "users") {
    def * = (userId, name, regKey) <> (UsersRow.tupled, UsersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (userId.?, name, regKey).shaped.<>({r=>import r._; _1.map(_=> UsersRow.tupled((_1.get, _2, _3)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column user_id DBType(INT), PrimaryKey, Default(0) */
    val userId: Column[Int] = column[Int]("user_id", O.PrimaryKey, O.AutoInc)
    /** Database column name DBType(VARCHAR), Length(255,true), Default(None) */
    val name: Column[Option[String]] = column[Option[String]]("name", O.Length(255,varying=true), O.Default(None))
    /** Database column reg_key DBType(VARCHAR), Length(150,true), Default(None) */
    val regKey: Column[Option[String]] = column[Option[String]]("reg_key", O.Length(150,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))
}