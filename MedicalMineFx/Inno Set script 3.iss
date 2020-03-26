; Script generated by the Inno Setup Script Wizard.
; SEE THE DOCUMENTATION FOR DETAILS ON CREATING INNO SETUP SCRIPT FILES!

#define MyAppName "Medical mine 8"
#define MyAppVersion "0.8"
#define MyAppPublisher "My Company, Inc."
#define MyAppExeName "MedicalMine_ver8.exe"

[Setup]
; NOTE: The value of AppId uniquely identifies this application. Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId={{93858857-AFD0-4D7F-9BE7-6E206FFDEC79}
AppName={#MyAppName}
AppVersion={#MyAppVersion}
;AppVerName={#MyAppName} {#MyAppVersion}
AppPublisher={#MyAppPublisher}
DefaultDirName={autopf}\Medical mine 9
DisableProgramGroupPage=yes
; Uncomment the following line to run in non administrative install mode (install for current user only.)
;PrivilegesRequired=lowest
OutputDir=C:\Users\GregoryThomas\Desktop\MedicalMineTest
OutputBaseFilename=Medical mine install 9
SetupIconFile=C:\DeLordSoftware\MedicalMine\MedicalMineFx\DeLordSoftwareConsultantIcon.ico
Compression=lzma
SolidCompression=yes
WizardStyle=modern

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked

[Files]
Source: "C:\DeLordSoftware\MedicalMine_ver8.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "C:\DeLordSoftware\MedicalMine\MedicalMineFx\dist\lib\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: "C:\DeLordSoftware\MedicalMine\MedicalMineFx\dist\MedicalMineFx.html"; DestDir: "{app}"; Flags: ignoreversion
Source: "C:\DeLordSoftware\MedicalMine\MedicalMineFx\dist\MedicalMineFx.jnlp"; DestDir: "{app}"; Flags: ignoreversion
; NOTE: Don't use "Flags: ignoreversion" on any shared system files

[Icons]
Name: "{autoprograms}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"
Name: "{autodesktop}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; Tasks: desktopicon

[Run]
Filename: "{app}\{#MyAppExeName}"; Description: "{cm:LaunchProgram,{#StringChange(MyAppName, '&', '&&')}}"; Flags: nowait postinstall skipifsilent
