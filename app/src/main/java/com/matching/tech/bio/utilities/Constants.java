package com.matching.tech.bio.utilities;

public class Constants {

    public static final Boolean isDemo = true;
    public static final String LOGIN_BY_PASSWORD = "Login with password";
    public static final String LOGIN_BY_FINGERPRINT = "Login with fingerprint";

    public static final String USER_NAME="userName";


    //////////////////AUTHENTICATE
    public static final String USER_ID="user-name";
    public static final String FINGER_IMAGE="finger-image";
    public static final String FINGER_POSITION="finger-position";
    public static final String APP_CODE="app-code";
    public static final String IP_ADDRESS="ipaddress";
    public static final String USER_TOKEN = "userToken";

    //////////VERIFY
    public static final String ID="id";
    public static final String FACE="face";
    public static final String IMAGE = "image";


    //////////VERIFY
    public static final String PERSON_ID="person-id";
    public static final String SAMIS_ID="samis-id";
    public static final String FINGERPRINT="fingerprint";
    public static final String FINGER_TYPE="finger-type";
    public static final String DOCUMENT = "document";
    public static final String NATIONALITY = "nationality";
    public static final String SAMIS_ID_MORAJAE="samisId";

    //Get Details
    public static final String ID_TYPE ="id-type";
    public static final String PERSON_TYPE ="person-type";
    public static final String AUTHORIZATION="Authorization";


    //Header Codes
    public static final String TRANSACTION_CODE = "Transaction-Code";
    public static final String WORKFLOW_CODE = "Workflow-Code";
    public static final String DEVICE_CODE = "Device-Code";
    public static final String WORKFLOW_TCN = "Workflow-Tcn";
    public static final String CLIENT_CODE = "Client-Code";
    public static final String LATITUDE = "Latitude";
    public static final String LONGITUDE = "Longtitude";
    public static final String CLIENT_VERSION = "Client-Version";
    public static final String CLIENT_OS = "Client-OS";

    //enrollment
    public static final String FINGERS ="fingers";
    public static final String FACE_IMAGE ="face-image";
    public static final String BIRTH_DATE = "brith-date";
    public static final String GENDER = "gender";
    public static final String SUPERVISOR_ID= "supervisor-id";
    public static final String MISSING = "missing";
    public static final String INQUIRY_ID =  "inquiry-id";
    public static final String FACE_SEARCH_IMAGE ="image";

    //Tasks
    public static final String AUTHENTICATION_TASK = "authenticationTask";
    public static final String MENU_LOOKUP_TASK = "menuLookupTask";
    public static final String NATIONALITY_LOOKUP_TASK = "nationalityLookupTask";
    public static final String REFRESH_TOKEN_TASK = "refreshTokenTask";
    public static final String ENROLLMENT_STATUS_TASK = "enrollmentStatusTask";
    public static final String GET_DETAILS_TASK = "getDetailsTask";
    public static final String ENROLLMENT_SUBMIT_TASK = "enrollmentSubmitTask";
    public static final String SUBMIT_ENROLLMENT_STATUS_TASK = "submitEnrollmentStatusTask";
    public static final String VERIFICATION_TASK = "verificationTask";
    public static final String SECURITY_CLEARANCE_TASK = "securityClearanceTask";
    public static final String SC_FINGERPRINT_AVAILABILITY_TASK = "fingersAvailabilityTask";
    public static final String IDENTIFICATION_TASK = "identificationTask";
    public static final String IDENTIFICATION_CHECK_STATUS_TASK = "identificationCheckStatusTask";
    public static final String FACE_SEARCH_TASK = "faceSearchTask";
    public static final String FACE_VERIFY_TASK = "faceVerifyTask";
    public static final String LOGOUT_TASK = "logoutTask";
    public static final String FACE_CAPTURE_TASK = "faceCapture";
    public static final String FINGERPRINT_CAPTURE_TASK = "fingerprintCaptureTask";
    public static final String DEVICE_NAME_TASK = "deviceName";
    public static final String DEAD_PERSONS_IDENTIFICATION_TASK = "deadPersonIdentificationTask";
    public static final String DEAD_PERSONS_IDENTIFICATION_CHECK_STATUS_TASK = "deadPersonIdentificationCheckStatusTask";
    public static final String DEAD_PERSONS_ENROLLMENT_TASK = "deadPersonEnrollmentTask";
    public static final String HAJJ_VERIFICATION_TASK = "hajjVerificationTask";
    public static final String HAJJ_ELIGIBILITY_TASK = "hajjEligibilityTask";
    public static final String HAJJ_VIOLATION_TASK = "hajjViolationTask";
    public static final String MORAJAE_VERIFICATION_TASK = "morajaeVerificationTask";
    public static final String MORAJAE_VERIFICATION_PERMIT_TASK = "morajaeVerificationPermitTask";
    public static final String MORAJAE_CHECK_PERMIT_TASK = "morajaeCheckPermitTask";
    public static final String MORAJAE_ENROLLMENT_TASK = "morajaeEnrollmentTask";
    public static final String GET_SAMIS_FROM_PASSPORT_NUMBER_TASK = "getSamisFromPassportNumberTask";
    public static final String GET_SECTORS_TASK = "getSectorsTask";
    public static final String SECURITY_CLEARANCE_BY_FACE_TASK = "securityClearanceByFaceTask";
    public static final String IDENTIFICATION_WANTED_CHECK_STATUS_TASK = "identificationWantedCheckStatusTask";

    //Frames or Fragments
    public static final String HOME = "home";
    public static final String GET_DETAILS = "getDetails";
    public static final String GET_DETAILS_RESULT = "getDetailsResult";
    public static final String FACE_CAPTURE = "faceCapture";
    public static final String LIVE_VIEW = "liveView";
    public static final String MISSING_FINGERS = "missingFingers";
    public static final String ENROLL_FINGERS = "enrollFingers";
    public static final String SUMMARY_FRAGMENT = "summaryFragment";
    public static final String ENROLL_RESULT = "enrollResult";
    public static final String VERIFICATION = "verification";
    public static final String VERIFICATION_RESULTS = "verificationResults";
    public static final String SECURITY_CLEARANCE = "securityClearance";
    public static final String SECURITY_CLEARANCE_RESULTS = "verifyWantedResults";
    public static final String MISSING_FINGERS_IDENTIFY = "missingFingers_Identify";
    public static final String IDENTIFICATION = "identification";
    public static final String IDENTIFY_DETAILS_RESULT = "identifyDetailsResult";
    public static final String PENDING_TRANSACTIONS = "pendingTransactions";
    public static final String FACE_SERACH_CAPTURE = "faceSearchCapture";
    public static final String FACE_SEARCH_RESULTS = "faceSearchResults";
    public static final String FACE_RECAPTURE= "faceRecapture";
    public static final String FACE_VERIFICATION = "faceVerifier";
    public static final String FACE_VERIFICATION_RESULTS = "faceVerificationResults" ;
    public static final String BARCODE = "scanBarcode";
    public static final String BARCODE_SCAN_VIEW = "barcodeLiveView";
    public static final String DEAD_PERSONS_FACE_CAPTURE = "deadPersonFaceRecapture";
    public static final String SC_FACE_VERIFICATION = "scFaceVerifier";
    public static final String SC_FACE_VERIFICATION_RESULTS = "scFaceVerificationResults";
    public static final String SUBMENU = "subMenu";
    public static final String SUBMENULIST = "subMenuList";
    public static final String MENU_MAP = "menuMap";

    public static final String MORAJAE = "morajae";
    public static final String MORAJAE_VERIFICATION = "morajaeVerification";
    public static final String MORAJAE_VERIFICATION_RESULTS = "morajaeVerificationResults";
    public static final String MORAJAE_REGISTER_RESULT = "morajaeRegisterResults";
    public static final String MORAJAE_PERMIT = "morajaePermit";
    public static final String MORAJAE_VERIFICATION_PERMIT = "morajaeVerificationPermit";
    public static final String MORAJAE_VERIFICATION_PERMIT_RESULTS = "morajaeVerificationPermitResults";

    public static final String MISSING_FINGERS_DEAD_PERSONS = "missingFingers_DeadPersons";
    public static final String IDENTIFICATION_DEAD_PERSONS = "identificationDeadPersons";
    public static final String DEAD_PERSONS_IDENTIFY_RESULT = "deadPersonIdentifyResult";
    public static final String MISSING_FINGERS_DEAD_PERSONS_ENROLL = "missingFingers_DeadPersons_Enroll";
    public static final String ENROLL_FINGERS_DEAD_PERSONS = "enrollFingers_DeadPersons";

    public static final String HAJJ_VERIFICATION = "hajjVerification";
    public static final String HAJJ_VERIFICATION_RESULTS = "hajjVerificationResults";


    //Authentication
    public static final String SUCCESS = "SUCCESS";

    //Innovatrics
    public static final String NO_FACE_DETECTED = "NO_FACE_DETECTED";
    public static final String YAW_ERROR = "YAW_ERROR";
    public static final String PITCH_ERROR = "PITCH_ERROR";
    public static final String ROLL_ERROR = "ROLL_ERROR";
    public static final String SHADOW_ERROR = "SHADOW_ERROR";
    public static final String RIGHT_EYE_CLOSED = "RIGHT_EYE_CLOSED";
    public static final String LEFT_EYE_CLOSED = "LEFT_EYE_CLOSED";
    public static final String EYE_GAZE_ERROR = "EYE_GAZE_ERROR";
    public static final String MULTIPLE_FACES_DETECTED = "MULTIPLE_FACES_DETECTED";
    public static final String DISPOSED = "DISPOSED";

    public static final String MINUTIAE_EXTRACTION_FAILED_DESC = "MINUTIAE_EXTRACTION_FAILED";

    public static final String WATSON_MINI = "WATSON MINI";
    public static final String AR = "ar";
    public static final String EN = "en";

    public static final int SUCCESS_CODE = 200;
    public static final int PERSON_NOT_ENROLLED_CODE = 500;
    public static final int OUT_WIDTH_FACE = 480;
    public static final int OUT_HEIGHT_FACE = 640;
    public static final int OUT_WIDTH_SEGMENTATION = 400;
    public static final int OUT_HEIGHT_SEGMENTATION = 500;
    public static final int WATSON_MINI_OUT_WIDTH_SEGMENTATION = 600;
    public static final int WATSON_MINI_OUT_HEIGHT_SEGMENTATION = 600;


    public static final String OPERATOR_ID = "operatorId";

    public static final String INTENSITY_TOO_LOW = "INTENSITY_TOO_LOW";
    public static final String INTENSITY_TOO_HIGH = "INTENSITY_TOO_HIGH";

    public static final String FEMALE_FACE_VIEWER_ROLE = "bio.app.femaleFace.viewer";


    public static final String FACE_DETECTION_OBJECT = "faceDetectionObj";
    public static final String ENROLL_STATUS = "enrollStatus";
    public static final String ENROLL_MESSAGE = "enrollMessage";
    public static final String ENROLL_ERROR = "enrollError";
    public static final String PENDING = "pending";
    public static final String FAILED = "failed";
    public static final String COMPLETED = "completed";
    public static final String VERIFICATION_RESULT = "verificationResult";
    public static final String MORAJAE_PERMIT_RESULT = "morajaePermitResult";
    public static final String ID_NUMBER = "idNumber";
    public static final String PERSON_INFO = "personInfo";
    public static final String FACEIMAGE = "faceImage";
    public static final String AVAILABLE_FINGERS = "availableFingers";
    public static final String SECURITY_CLEARANCE_RESULT = "securityClearanceResult";
    public static final String BARCODE_TEXT = "barcode";
    public static final String IS_MATCHING = "isMatching";
    public static final String SAMSUNG_DEVICE = "samsung";

    public static final String VISITOR_ID_NUMBER = "visitorId";
    public static final String VISITOR_NATIONALITY = "visitorNationality";
    public static final String VISITOR_NAME= "visitorName";
    public static final String VISIT_PURPOSE = "visitPurpose";
    public static final String VISITING_PERSON_NAME = "visitedName";
    public static final String VISITING_PERSON_ID = "visitedPersonId";
    public static final String VISITING_DEPARTMENT = "visitedDept";
    public static final String OPT_ID = "optId";
    public static final String LOCATION_ID = "locationId";
    public static final String LOCATION_IP = "locationIp";
    public static final String IS_MATHING = "isMatching";
    public static final String PERMIT_NUMBER = "permitNumber";
    public static final String IS_RECORD_INSERTED = "isRecordInserted";
    public static final String HAJJ_ELIGIBILITY = "hajjEligibility";
    public static final String PASSPORT_NUMBER = "passportNumber";

    public static final String SECTORS = "sectors";
    public static final String WANTED_CODES = "wantedCodes";

    public static final int LOCATION_PERMISSION_REQUEST_ID = 2442;
    public static final int MINIMUM_TIME_BETWEEN_LOCATION_UPDATES = 500;
    public static final int MINIMUM_DISTANCE_BETWEEN_LOCATION_UPDATES = 0;

    public static final String CLIENT_OS_VALUE = "Android";
    public static final String CLIENT_VERSION_VALUE = "2018.11.1";
    public static final String CLIENT_CODE_VALUE = "BN";
    public static final Integer TRANSACTION_CODE_VALUE = null;
    public static final Integer WORKFLOW_CODE_VALUE = null;
    public static final Long WORKFLOW_TCN_VALUE = null;


    public static final String PERMIT_NOT_FOUND = "PERMIT_NOT_FOUND";
    public static final String PERMIT_EXPIRED = "PERMIT_EXPIRED";
    public static final String NO_PERMISSION_ON_THIS_SECTORS = "NO_PERMISSION_ON_THIS_SECTORS";
    public static final String THIS_OPERATOR_REGISTER_TO_ANOTHER_SECTOR = "THIS_OPERATOR_REGISTER_TO_ANOTHER_SECTOR";


    public static final String PERMIT_INFORMATION = "permitInformation";
    public static final String SECTORS_INFORAMTION = "sectorsInformation";
    public static final String PERMITTED_PERSONS = "permittedPersons";
    public static final String PERSON_INFORMATION = "personInformation";
    public static final String WANTED_INFORAMTION = "wantedInformation";


    public static final String VEHICLE_INFORMATION = "vehicleInformation";
    public static final String ITEMS_INFORMATION = "itemsInformation";
    public static final String PERMIT_STATUS = "PermitStatus";

    public static final String REGULAR = "regular";
    public static final String BOLD = "bold";
    public static final String SEMI_BOLD = "semi-bold";


    public static final String VERIFICATION_M = "verification";
    public static final String INQUIRY_M = "inquiry";
    public static final String ENROLLMENT_M = "enrollment";
    public static final String MORAJAE_M = "morajae";
    public static final String SECURITYCLEARANCE_M = "securityClearance";
    public static final String HAJJ_M = "hajj";
    public static final String PENDINGTRANSACTIONS_M = "pendingTransactions";
    public static final String LOGOUT_M = "logout";

    public static final String MENU_VERIFICATION_FINGERPRINT = "menu.verification.fingerprint";
    public static final String MENU_VERIFICATION_FACE= "menu.verification.face";
    public static final String MENU_INQUIRY_FINGERPRINT = "menu.inquiry.fingerprint";
    public static final String MENU_INQUIRY_FACE = "menu.inquiry.face";
    public static final String MENU_INQUIRY_DEAD_PERSONS = "menu.inquiry.deadPersons";
    public static final String MENU_SECURITY_CLEARANCE_FINGERPRINT = "menu.securityClearance.fingerprint";
    public static final String MENU_SECURITY_CLEARANCE_FACE = "menu.securityClearance.face";
    public static final String MENU_SECURITY_CLEARANCE_FINGERPRINT_INQUIRY = "menu.securityClearance.fingerprintInquiry";
    public static final String MENU_MORAJAE_REGISTER_MORAJAE = "menu.morajae.registerMorajae";
    public static final String MENU_MORAJAE_MORAJAE_PERMIT = "menu.morajae.morajaePermit";
    public static final String MENU_ENROLLMENT_CITIZEN = "menu.enrollment.citizen";
    public static final String MENU_ENROLLMENT_NEW_BORN = "menu.enrollment.newBorn";
    public static final String MENU_HAJJ_HAJJPERMIT = "menu.hajj.hajjPermit";
    public static final String MENU_PENDING_TRANSACTION = "menu.pendingTransactions";
    public static final String MENU_LOGOUT = "menu.logout";


    public static final String FACE_TEXT = "face";
    public static final String FINGERPRINT_TEXT = "fingerprint";
    public static final String DEAD_PERSONS_TEXT = "deadPersons";
    public static final String REGISTER_MORAJAE_TEXT = "registerMorajae";
    public static final String MORAJAE_PERMIT_TEXT = "morajaePermit";
    public static final String CITIZEN_TEXT = "citizen";
    public static final String NEW_BORN_TEXT = "newBorn";
    public static final String HAJJ_PERMIT_TEXT = "hajjPermit";
    public static final String PENDING_TRANSACTIONS_TEXT = "pendingTransactions";
    public static final String LOGOUT_TEXT = "logout";
    public static final String FINGERPRINT_INQUIRY_TEXT = "fingerprintInquiry";


    public static final int privateMode = 0;
    public static final String preferenceName = "BananPref";
    public static final String isUserLoggedIn = "IsUserLoggedIn";
    public static final String userId = "userId";
    public static final String operatorId = "operatorId";
    public static final String locationId = "locationId";
    public static final String token = "token";
    public static final String operatorName = "operatorName";
    public static final String operatorFace = "operatorFace";
    public static final String operatorRoles = "operatorRoles";
    public static final String menuLookups = "menuLookups";
    public static final String language = "language";
    public static final String nationalities = "nationalities";
    public static final String sectors = "sectors";



}
