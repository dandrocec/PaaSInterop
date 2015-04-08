USE [master]
GO
/****** Object:  Database [credits2]    Script Date: 6.11.2012. 11:36:56 ******/
CREATE DATABASE [credits2]
GO
ALTER DATABASE [credits2] SET COMPATIBILITY_LEVEL = 100
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [credits2].[dbo].[sp_fulltext_database] @action = 'disable'
end
GO
ALTER DATABASE [credits2] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [credits2] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [credits2] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [credits2] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [credits2] SET ARITHABORT OFF 
GO
ALTER DATABASE [credits2] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [credits2] SET AUTO_CREATE_STATISTICS ON 
GO
ALTER DATABASE [credits2] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [credits2] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [credits2] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [credits2] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [credits2] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [credits2] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [credits2] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [credits2] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [credits2] SET  DISABLE_BROKER 
GO
ALTER DATABASE [credits2] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [credits2] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [credits2] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [credits2] SET ALLOW_SNAPSHOT_ISOLATION ON 
GO
ALTER DATABASE [credits2] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [credits2] SET READ_COMMITTED_SNAPSHOT ON 
GO
ALTER DATABASE [credits2] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [credits2] SET RECOVERY FULL 
GO
ALTER DATABASE [credits2] SET  MULTI_USER 
GO
ALTER DATABASE [credits2] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [credits2] SET DB_CHAINING OFF 
GO
USE [credits2]
GO
/****** Object:  Table [dbo].[Credit]    Script Date: 6.11.2012. 11:36:56 ******/
SET ANSI_NULLS OFF
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Credit](
	[CreditId] [int] IDENTITY(1,1) NOT NULL,
	[label] [varchar](11) NULL,
	[openingDate] [datetime] NULL,
	[expirationDate] [datetime] NULL,
	[amountKn] [decimal](18, 2) NULL,
	[amountCurrency] [decimal](18, 2) NULL,
	[customer_CustomerID] [int] NULL,
 CONSTRAINT [PrimaryKey_7f5cb4cd-3b99-4a1e-ae19-f7617f32f745] PRIMARY KEY CLUSTERED 
(
	[CreditId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
)

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Customer]    Script Date: 6.11.2012. 11:36:56 ******/
SET ANSI_NULLS OFF
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Customer](
	[CustomerID] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](255) NULL,
	[address] [varchar](255) NULL,
	[phoneNumber] [varchar](50) NULL,
 CONSTRAINT [PrimaryKey_907489c6-704b-4955-a584-dee9cf6c6a2e] PRIMARY KEY CLUSTERED 
(
	[CustomerID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
)

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Warning]    Script Date: 6.11.2012. 11:36:56 ******/
SET ANSI_NULLS OFF
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Warning](
	[WarningID] [int] IDENTITY(1,1) NOT NULL,
	[warningDate] [datetime] NULL,
	[warningNumber] [int] NULL,
	[credit_CreditID] [int] NULL,
 CONSTRAINT [PrimaryKey_3a1447a4-71d3-4e31-b691-e0d8fe33cb64] PRIMARY KEY CLUSTERED 
(
	[WarningID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
)

GO
ALTER TABLE [dbo].[Credit]  WITH CHECK ADD  CONSTRAINT [FK_Credit_0] FOREIGN KEY([customer_CustomerID])
REFERENCES [dbo].[Customer] ([CustomerID])
GO
ALTER TABLE [dbo].[Credit] CHECK CONSTRAINT [FK_Credit_0]
GO
ALTER TABLE [dbo].[Warning]  WITH CHECK ADD  CONSTRAINT [FK_Warning_0] FOREIGN KEY([credit_CreditID])
REFERENCES [dbo].[Credit] ([CreditId])
GO
ALTER TABLE [dbo].[Warning] CHECK CONSTRAINT [FK_Warning_0]
GO
USE [master]
GO
ALTER DATABASE [credits2] SET  READ_WRITE 
GO
