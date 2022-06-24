# gameapi_daos

ver1.7.9
* add game and gameType with zh_CN

ver1.7.8
* add partitionInfo, removePartition

ver1.7.7
* add SysSettingDao

ver1.7.6
* update IP2LOCATION file

ver1.7.5
* getJackpotReportByAgent3

ver1.7.4
* get onlinePlayer by token

ver1.7.3
* add getAllGameVersionListWithLanguage
* add logVersionRecordWithLanguage

ver1.7.2
* addCamAward, getCamAward add CAM_ImgID,CAM_AwardDescription
* gameUrl version add language

ver1.7.1
* gameDao getGameByGameCodeAndLanguage language column as gameName

ver1.7.0
* add getAllEnableGameListByLanguage

ver1.6.9
* add campaign dao
* add LaniInfoDAO
* gameDao getGameUrl add languge gameName

ver1.6.8
* monitorBackgroundJob -> zabbix

ver1.6.7
* gameapi-commons set IP2LocationUtil IPV6-COUNTRY.BIN fiel name 

ver1.6.6
* gameapi-commons add ip2LocationUtil2

ver1.6.5
* add get2MonthGameCodeByAgent3List

ver1.6.4
* add and get FailureTypeCount DAO

ver1.6.3
* getFailureRateByGameType remove selectForUpdate

ver1.6.2
* getFailureRateByGameType add rowset condition

ver1.6.1
* add failurerate dao

ver1.6.0
* getRoomGameDetail and getRoomGameByPlayerGameCode add roomName

ver1.5.9
* add getAllAgentTree
* add getJackpotReportByPlyguidList
* add getJackpotReportByAgent1List

ver1.5.8
* gameapi-commons add frontendapi error code game not enable

ver1.5.7
* add AggregationSummaryJackpotContribute

ver1.5.6
* modify api jackpotStatus
* add GAmeDAO getLobbyUrl

ver1.5.5
* tgadmin add version record

ver1.5.4
* add sysFun
* add getAllMaintainGameList

ver1.5.3
>>>>>>> develop
* adjust calculate for new Retention V3

ver1.5.2
* adjust calculate for new Retention

ver1.5.1
* upgrade version

ver1.5.0
* adjust GameReportDayDAO for new retention

ver1.4.9
* JackpotReportDAO add getJackpotAgentReport
* add getJackpotPlayerRecord
* fixed getAgent3ListByAgentGuidAndLevel empty

ver1.4.8
* fish add jp,jpcontribute,jptype天九紅包,jpredis

ver1.4.7
* DailyTradeDAO'getSinglePlayerSummaryReportByAgent3List add jackpot2&3

ver1.4.6
* add getAggItemsByPlyGuidAdnGameCode
* backgroundjob redoGameReport, redoGameReportDay add jackpotType3
* backgroundjob sumarryAllGame add jackpotType3
* updateGameMultiplierList add byDomain and allAgent
* gameSettingDAO add new param
* gameReportDAO add getAgentGameReport
* gameReportDAO add jackpotType3
* gameReportDayDAO add jackpotType3
* modify getAgentPlayerOnlineCount
* add getOnlinePlayerGroupByAgent

ver1.4.5
* reference 1.1.9 gameapi-commons

ver1.4.4
* adjust GameReportDayDAO'getPlayerDailyReportByAgent sql

ver1.4.3
* add RtpUtil

ver1.4.2
* tgadmin add jackpot2 type

ver1.4.1
* fix backendapi getNetWinGroupByDate
* GR_CardGame add roomName

ver1.4.0
* gameapi-commons add responseCode GAMEAPI_TRIALPLAYGAME_NOT_ALLOW
* add GameRecordDAO jackpottype2
* gameRecordDAO.getRoomGameByAgent
* gameRecordDAO add RoomGame type

ver1.3.9
* add redisUtil method -> publish

ver1.3.8
* add GameReportDayDAO'getGameReportDayByAgent3

ver1.3.7
* add gameTicketDAO.getPlayingGameCode
* GameReportDAO add getHistorySumBetsAndSumNetWin

ver1.3.6
* 註解掉jackpot2

ver1.3.5
* MemPlayerDAO add getPlayerPointList
* add MemPlayerDAO'getOnlinePlayerCountByAgent3
* add MemPlayerDAO'getOnlinePlayerAccountIdsByAgent3
* add tgadmin get GameReportDayByAgent3

ver1.3.4
* add LogGameTicketDAO'getLogDateAndAndGamecodeAndLogPlayerIdList for Retention CPU usage improve
* add GameReportDayDAO'getPlayerIdListByDateAndGamecode for Retention CPU usage improve

ver1.3.3
* add LogGameTicketDAO'getLogDateAndLogPlayerIdList for Retention
* add GameReportDayDAO'getPlayerIdListByDate for Retention
* gameDAO add eventVersion
* add jackpot2 column

ver1.3.2
* add GameRecordDAO getMiniGameByPlayerGameCode sql select gameRecord

ver1.3.1
* add geSummaryBYday exludeDomain
* getPlayerDailyReportByAgent add jackpot2
* gameReport, gameReportDay add jackpot2
* getGameByPlayerGameCode add jackpotType
* gameDetail add jackpotType
* getAllAgentByMctGuid add domain
* add getJackpotSettingByDomain
* add JackpotDAO

ver1.3.0
* modify  playerDailyRepor endTime condition

ver1.2.9
* add playerDailyReport and summaryPlayerReprot

ver1.2.8
* add GameSettingDAO'getMultiplierListByAgtGuidAndGameCode
* add GameSettingDAO'updateGameMultiplierList

ver1.2.7
* jump over 1.2.6

ver1.2.5
* gameapi-commins add projectType tgtunnel

ver1.2.4
* gameReport,fishRoundDown,summaryByDateByGameCode,summaryByDateByGameType add exclude agent

ver1.2.3
* add GameRecordDAO'getFishingByPlayerForAnalysis
* modify DailyTradeDAO'Sql order by DT_CreateDatetime instead of DT_UpdateTime
* modify GameRecordDAO add extraType&betType for miniGame
* add gameReportDayDAO'getGameTypeByTimeAndAgent1 

ver1.2.2
* modify getFishRoundDown

ver1.2.1
* add getFishRoundDown
* fishAnalysis add json column

ver1.2.0
* gamerecord add fishAnalysis
* add fishDao

ver1.1.9
* addMerchant add isMaintain
* modify getPlayGuidByGameCodeAndAgent1List createDateTime plus =
* gameReportDayDao add getSummaryByDay
* gameReportDayDao add getSummaryByDayByGameCode
* gameReportDayDao add getSummaryByDayByGameType

ver1.1.8
* add getPlyGuidByGameCodeAndAgent1List
* add kickAndLogoutPlayer
* lastReportDate add getIndex without time
* add merchan updateIsMaintain
* add reportError join log_report
* modify check partition remove out param
* getMerchant add isMaintain
* LogGameTicket limit 2 day
* getJsonBySeq from dailytradeDao to gameRecordDao 
* gameDao add method changeGameMaintain
* getJsonBySeq from dailytradeDao to gameRecordDao
* add getAllEnableGameList

ver1.1.7
* getAllMerchant add domain
* refactor sql's in and = phrase 
* singlePlayerSummaryReport use search index PLY_GUID
* getDailyTradeListCount2 add tradePoint condition

ver1.1.6
* add GameReportDAO'getSummaryAgentReport
* add GameReportDayDAO'getSummaryAgentReportDay
* add AgentDAO'getAgentByDomain
* dailytrade modify dailytradeListByAccCode add search column
* agentDao add getAgentByDomainAndPlayerID
* add getSinglePlayerSummaryReportByAgent3List

ver1.1.5
* add gameReportByAgent to hour

ver1.1.4
* gameapi-commons add new ProjectTypeData SENDELKAPI

ver1.1.3
* add getDiffCount
* add getSinglePlayerReport no gameType
* add GameReportDAO
* add GameRecordDAO MiniGame
* add GameReportDayDAO test_case
* add GameReportDayDAO
* split GameReportDAO to GameReportDayDAO

ver1.1.2
* add get diffcount record

ver1.1.1
* add gameReport, summaryReport for merchant

ver1.1.0
* add get and update UserConfig
* add TODO follow DAO rule

ver1.0.9
* add log tgadmin action

ver1.0.8
* add getGameReportByAgent3

ver1.0.7
* modify playerDetail for multi player
* modify checkDiffCountExistByDay sql not equals zero

ver1.0.6
* modify get agent tree
* modify dailytradelist to multiple playerid
* add get agentByAccountIDAndParent
* add get managerList
* modify MemPlayerDAO'getPlayerList Sql for agentLevel
* modify MemPlayerDAO'getPlayerListCount Sql for agentLevel
* modify GameReportDAO for playerAccount&GameCode
* add get transferlist,playerdetail,gamedetail,dailytradelist by agent level
* add MemPlayerDAO checkUserExistByAgent
* add GameReportDAO getSinglePlayerSummaryReport
* modify TransferRecordDAO; getTradeRecordByTransferID、getTradeRecordByQueryAndLevel add orgTree
* modify DailyTradeDAO sql timediff()
* add GameReportErrorDAO'checkDiffCountExistByDay

ver1.0.5
* modify dailyTrade add agtAccountID
* add getloginlog

ver1.0.4
* modify gameapi-commons's gameTypeData

ver1.0.3
* change playerDailyReport bets to bet
* add playerDailyReport
* add GameReportDAO'clearGameReportDayByDate
* add GameReportDAO'clearGameReportDayByDateAndAgent3
* add GameReportDAO'getPlayerDataByDate
* add GameReportDAO'getPlayerDataByDateAndAgent3
* merge GamesDAO to GameDAO
* remove GamesDAO
* add GameTypeModel
* add GameTypeDAO'addGameType
* add GameDAO'addSysGame
* add AgentGameRelationDAO'addGameTypeByMctGuid
* modify getMaxPartition, createDateTime add MAX
* add DT_GameReportDay
* add create partition by call create_partition_by_number
* add get max partition list
* add partition warn if call auto create partition failed

ver1.0.2
* add updateGameTypeByMctGuid
* add and update gameSetting

ver1.0.1
* modify partition check

ver1.0.0
* first release
